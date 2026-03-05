package com.noahhendrickson.gateway.diddy;

import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MessageReceivedListener extends ListenerAdapter {

    private static final Logger log = LoggerFactory.getLogger(MessageReceivedListener.class);

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.isWebhookMessage()) return;
        if (!event.isFromGuild()) return;
        if (!event.getMessage().getContentStripped().toLowerCase().contains("lube")) return;


        MessageChannelUnion channel = event.getChannel();
        log.info(event.getAuthor().getName()+" " + event.getMessage().getContentStripped());
        channel.sendMessage("DID SOMEONE SAY LUBE!!! WHERES THE INV \uD83E\uDDF4").queue();
        channel.sendMessage("https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExcG13b3o2bHdveGZmeGZzdWIyeWNmeWlmMTAwYzg3amhpbGJjcGhkOCZlcD12MV9naWZzX3NlYXJjaCZjdD1n/KtLmwz91Cjf5KvfVgd/giphy.gif").queue();



    }
}
