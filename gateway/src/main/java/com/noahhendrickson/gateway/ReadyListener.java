package com.noahhendrickson.gateway;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ReadyListener extends ListenerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ReadyListener.class);

    @Override
    public void onReady(ReadyEvent event) {
        log.info("Bot online: {}", event.getJDA().getSelfUser().getName());
    }
}
