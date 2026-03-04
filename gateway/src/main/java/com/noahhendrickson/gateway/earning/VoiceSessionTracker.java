package com.noahhendrickson.gateway.earning;

import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class VoiceSessionTracker {

    private final ConcurrentHashMap<VoiceKey, Instant> sessions = new ConcurrentHashMap<>();

    public void join(long userId, long guildId) {
        sessions.putIfAbsent(new VoiceKey(userId, guildId), Instant.now());
    }

    public void leave(long userId, long guildId) {
        sessions.remove(new VoiceKey(userId, guildId));
    }

    public Map<VoiceKey, Instant> snapshot() {
        return Map.copyOf(sessions);
    }
}
