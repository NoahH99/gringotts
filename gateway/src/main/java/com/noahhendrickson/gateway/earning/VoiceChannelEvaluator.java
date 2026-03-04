package com.noahhendrickson.gateway.earning;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class VoiceChannelEvaluator {

    private final VoiceSessionTracker tracker;

    public VoiceChannelEvaluator(VoiceSessionTracker tracker) {
        this.tracker = tracker;
    }

    public void evaluate(AudioChannel channel, Guild guild) {
        if (isAfkChannel(channel, guild)) return;

        List<Member> nonBots = channel.getMembers().stream()
                .filter(m -> !m.getUser().isBot())
                .toList();

        Set<Long> eligibleIds = nonBots.stream()
                .filter(m -> m.getVoiceState() != null
                        && !m.getVoiceState().isDeafened()
                        && !m.getVoiceState().isSelfDeafened())
                .map(m -> m.getUser().getIdLong())
                .collect(Collectors.toSet());

        if (eligibleIds.size() >= 2) {
            nonBots.stream()
                    .filter(m -> !eligibleIds.contains(m.getUser().getIdLong()))
                    .forEach(m -> tracker.leave(m.getUser().getIdLong(), guild.getIdLong()));
            eligibleIds.forEach(id -> tracker.join(id, guild.getIdLong()));
        } else {
            nonBots.forEach(m -> tracker.leave(m.getUser().getIdLong(), guild.getIdLong()));
        }
    }

    public void removeUser(long userId, long guildId) {
        tracker.leave(userId, guildId);
    }

    public boolean isAfkChannel(AudioChannel channel, Guild guild) {
        VoiceChannel afk = guild.getAfkChannel();
        return afk != null && afk.getIdLong() == channel.getIdLong();
    }
}
