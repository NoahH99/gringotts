package com.noahhendrickson.persistence.sync;

import com.noahhendrickson.persistence.entity.GuildEconomyConfigEntity;
import com.noahhendrickson.persistence.entity.GuildEntity;
import com.noahhendrickson.persistence.repository.GuildEconomyConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GuildEconomyConfigProvisionService {

    private final GuildEconomyConfigRepository configRepository;

    public GuildEconomyConfigProvisionService(GuildEconomyConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public GuildEconomyConfigEntity ensure(GuildEntity guild) {
        return configRepository.findById(guild.getId())
                .orElseGet(() -> {
                    GuildEconomyConfigEntity config = new GuildEconomyConfigEntity();
                    config.setGuild(guild);
                    return configRepository.save(config);
                });
    }
}
