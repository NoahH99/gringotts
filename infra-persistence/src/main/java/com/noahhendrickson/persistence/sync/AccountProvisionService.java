package com.noahhendrickson.persistence.sync;

import com.noahhendrickson.persistence.entity.AccountEntity;
import com.noahhendrickson.persistence.entity.AccountType;
import com.noahhendrickson.persistence.entity.GuildMemberEntity;
import com.noahhendrickson.persistence.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountProvisionService {

    private final AccountRepository accountRepository;

    public AccountProvisionService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountEntity ensureWallet(GuildMemberEntity member) {
        return accountRepository.findByMemberIdAndAccountType(member.getId(), AccountType.WALLET)
                .orElseGet(() -> {
                    AccountEntity wallet = new AccountEntity();
                    wallet.setMember(member);
                    wallet.setAccountType(AccountType.WALLET);
                    wallet.setBalance(0L);
                    return accountRepository.save(wallet);
                });
    }
}
