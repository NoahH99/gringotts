package com.noahhendrickson.gateway.diddy;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MessageReceivedListener extends ListenerAdapter {

    private static final Logger log = LoggerFactory.getLogger(MessageReceivedListener.class);
    private static final String DIDDY_GIF = "https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExcG13b3o2bHdveGZme" +
            "GZzdWIyeWNmeWlmMTAwYzg3amhpbGJjcGhkOCZlcD12MV9naWZzX3NlYXJjaCZjdD1n/KtLmwz91Cjf5KvfVgd/giphy.gif";
    private static final String LUBE_BOTTLE_EMOJI = " \uD83E\uDDF4";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();

        if (author.isBot() || event.isWebhookMessage()) return;
        if (!event.isFromGuild()) return;
        if (!event.getMessage().getContentStripped().toLowerCase().contains("lube")) return;

        MessageChannelUnion channel = event.getChannel();

        channel.sendMessage("DID SOMEONE SAY LUBE!!! WHERES THE INV " + LUBE_BOTTLE_EMOJI).queue();
        channel.sendMessage(DIDDY_GIF).queue();

        log.info("User {} ({}) said: {}", author.getName(), author.getId(), event.getMessage().getContentRaw());
    }
}
