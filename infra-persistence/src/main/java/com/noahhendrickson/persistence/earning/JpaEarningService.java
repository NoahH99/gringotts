package com.noahhendrickson.persistence.earning;

import com.noahhendrickson.kernel.earning.EarningEvent;
import com.noahhendrickson.kernel.earning.EarningSource;
import com.noahhendrickson.ledger.earning.EarningService;
import com.noahhendrickson.persistence.entity.*;
import com.noahhendrickson.persistence.repository.EarningCooldownRepository;
import com.noahhendrickson.persistence.repository.LedgerEntryRepository;
import com.noahhendrickson.persistence.sync.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class JpaEarningService implements EarningService {

    private static final Logger log = LoggerFactory.getLogger(JpaEarningService.class);

    private final UserSyncService userSyncService;
    private final GuildSyncService guildSyncService;
    private final GuildEconomyConfigProvisionService configProvisionService;
    private final GuildMemberSyncService memberSyncService;
    private final AccountProvisionService accountProvisionService;
    private final EarningCooldownRepository cooldownRepository;
    private final LedgerEntryRepository ledgerEntryRepository;
    private final Map<EarningSource, EarningStrategy> strategies;

    public JpaEarningService(
            UserSyncService userSyncService,
            GuildSyncService guildSyncService,
            GuildEconomyConfigProvisionService configProvisionService,
            GuildMemberSyncService memberSyncService,
            AccountProvisionService accountProvisionService,
            EarningCooldownRepository cooldownRepository,
            LedgerEntryRepository ledgerEntryRepository,
            List<EarningStrategy> strategyList
    ) {
        this.userSyncService = userSyncService;
        this.guildSyncService = guildSyncService;
        this.configProvisionService = configProvisionService;
        this.memberSyncService = memberSyncService;
        this.accountProvisionService = accountProvisionService;
        this.cooldownRepository = cooldownRepository;
        this.ledgerEntryRepository = ledgerEntryRepository;
        this.strategies = new EnumMap<>(EarningSource.class);
        strategyList.forEach(s -> this.strategies.put(s.source(), s));
    }

    @Override
    public long tryAward(EarningEvent event) {
        EarningStrategy strategy = strategies.get(event.source());

        UserEntity user = userSyncService.sync(event.snapshot());
        GuildEntity guild = guildSyncService.sync(event.snapshot());
        GuildEconomyConfigEntity config = configProvisionService.ensure(guild);
        GuildMemberEntity member = memberSyncService.sync(user, guild, event.snapshot());

        EarningCooldownId cooldownId = new EarningCooldownId(member.getId(), event.source());
        OffsetDateTime now = OffsetDateTime.now();

        EarningCooldownEntity cooldown = cooldownRepository.findByIdForUpdate(cooldownId).orElse(null);
        if (isOnCooldown(cooldown, strategy, config, now)) return 0;
        if (!strategy.isEligible(event, config)) return 0;

        long amount = strategy.computeAmount(event, config);
        if (amount == 0) return 0;

        AccountEntity wallet = accountProvisionService.ensureWallet(member);
        credit(wallet, amount, event, strategy);
        upsertCooldown(cooldown, cooldownId, member, now);

        log.debug("Awarded {} Aura via {} to user {} in guild {}",
                amount, event.source(), event.snapshot().userId(), event.snapshot().guildId());
        return amount;
    }

    private boolean isOnCooldown(EarningCooldownEntity cooldown, EarningStrategy strategy,
                                 GuildEconomyConfigEntity config, OffsetDateTime now) {
        if (cooldown == null) return false;
        return cooldown.getLastAwardedAt().plusSeconds(strategy.cooldownSecs(config)).isAfter(now);
    }

    private void credit(AccountEntity wallet, long amount, EarningEvent event, EarningStrategy strategy) {
        wallet.setBalance(wallet.getBalance() + amount);

        LedgerEntryEntity entry = new LedgerEntryEntity();
        entry.setAccount(wallet);
        entry.setAmount(amount);
        entry.setBalanceAfter(wallet.getBalance());
        entry.setEventType(strategy.eventType());
        entry.setMetadata(Map.of(
                "userId", event.snapshot().userId(),
                "guildId", event.snapshot().guildId(),
                "source", event.source().name()
        ));
        ledgerEntryRepository.save(entry);
    }

    private void upsertCooldown(EarningCooldownEntity cooldown, EarningCooldownId cooldownId,
                                GuildMemberEntity member, OffsetDateTime now) {
        if (cooldown == null) {
            cooldown = new EarningCooldownEntity();
            cooldown.setId(cooldownId);
            cooldown.setMember(member);
        }
        cooldown.setLastAwardedAt(now);
        cooldownRepository.save(cooldown);
    }
}
