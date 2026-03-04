package com.noahhendrickson.gateway.listeners;

import com.noahhendrickson.gateway.lifecycle.BotLifecycleService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ReadyListener extends ListenerAdapter {

    private final BotLifecycleService lifecycleService;

    public ReadyListener(BotLifecycleService lifecycleService) {
        this.lifecycleService = lifecycleService;
    }

    @Override
    public void onReady(ReadyEvent event) {
        lifecycleService.onReady(event.getJDA().getSelfUser().getName());
    }
}
