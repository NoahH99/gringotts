package com.noahhendrickson.gateway;

import com.noahhendrickson.kernel.bot.BotActivity;
import com.noahhendrickson.kernel.bot.BotPresenceService;
import com.noahhendrickson.kernel.bot.BotStatus;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ReadyListener extends ListenerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ReadyListener.class);

    private final BotPresenceService presenceService;

    public ReadyListener(BotPresenceService presenceService) {
        this.presenceService = presenceService;
    }

    @Override
    public void onReady(ReadyEvent event) {
        log.info("Bot online: {}", event.getJDA().getSelfUser().getName());
        presenceService.setPresence(BotStatus.READY, BotActivity.watching("your Gringotts account"));
    }
}
