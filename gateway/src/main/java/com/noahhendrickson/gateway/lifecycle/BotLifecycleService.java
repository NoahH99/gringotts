package com.noahhendrickson.gateway.lifecycle;

import com.noahhendrickson.kernel.bot.BotActivity;
import com.noahhendrickson.kernel.bot.BotPresenceService;
import com.noahhendrickson.kernel.bot.BotStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BotLifecycleService {

    private static final Logger log = LoggerFactory.getLogger(BotLifecycleService.class);

    private final BotPresenceService presenceService;

    public BotLifecycleService(BotPresenceService presenceService) {
        this.presenceService = presenceService;
    }

    public void onReady(String botName) {
        log.info("Bot online: {}", botName);
        presenceService.setPresence(BotStatus.MAINTENANCE, BotActivity.playing("Currently in development..."));
    }
}
