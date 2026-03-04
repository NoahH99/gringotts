package com.noahhendrickson.persistence.sync;

import com.noahhendrickson.kernel.sync.DiscordEntitySnapshot;
import com.noahhendrickson.persistence.entity.GuildEntity;
import com.noahhendrickson.persistence.repository.GuildRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GuildSyncService {

    private final GuildRepository guildRepository;

    public GuildSyncService(GuildRepository guildRepository) {
        this.guildRepository = guildRepository;
    }

    public GuildEntity sync(DiscordEntitySnapshot snapshot) {
        return guildRepository.findById(snapshot.guildId())
                .map(guild -> {
                    guild.setName(snapshot.guildName());
                    guild.setIconHash(snapshot.guildIconHash());
                    guild.setOwnerId(snapshot.guildOwnerId());
                    return guildRepository.save(guild);
                })
                .orElseGet(() -> {
                    GuildEntity guild = new GuildEntity();
                    guild.setId(snapshot.guildId());
                    guild.setName(snapshot.guildName());
                    guild.setIconHash(snapshot.guildIconHash());
                    guild.setOwnerId(snapshot.guildOwnerId());
                    return guildRepository.save(guild);
                });
    }
}
