package com.noahhendrickson.gateway.earning;

import com.noahhendrickson.kernel.earning.VoiceEarningApplicationEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

@Component
public class VoiceTickScheduler {

    private final ObjectProvider<JDA> jdaProvider;
    private final VoiceSessionTracker tracker;
    private final EarningEventFactory factory;
    private final ApplicationEventPublisher eventPublisher;

    public VoiceTickScheduler(ObjectProvider<JDA> jdaProvider, VoiceSessionTracker tracker,
                               EarningEventFactory factory, ApplicationEventPublisher eventPublisher) {
        this.jdaProvider = jdaProvider;
        this.tracker = tracker;
        this.factory = factory;
        this.eventPublisher = eventPublisher;
    }

    @Scheduled(fixedDelay = 60_000)
    public void tick() {
        JDA jda = jdaProvider.getIfAvailable();
        if (jda == null) return;
        if (jda.getStatus() != JDA.Status.CONNECTED) return;

        for (Map.Entry<VoiceKey, Instant> entry : tracker.snapshot().entrySet()) {
            VoiceKey key = entry.getKey();
            Instant joinedAt = entry.getValue();

            Guild guild = jda.getGuildById(key.guildId());
            if (guild == null) {
                tracker.leave(key.userId(), key.guildId());
                continue;
            }

            User user = jda.getUserById(key.userId());
            if (user == null) {
                tracker.leave(key.userId(), key.guildId());
                continue;
            }

            Member member = guild.getMemberById(key.userId());
            long sessionSecs = Duration.between(joinedAt, Instant.now()).toSeconds();

            eventPublisher.publishEvent(new VoiceEarningApplicationEvent(
                    factory.buildVoiceEvent(user, guild, member, sessionSecs)
            ));
        }
    }
}
