package com.noahhendrickson.kernel.bot;

public interface BotPresenceService {
    void setPresence(BotStatus status, BotActivity activity);
}
