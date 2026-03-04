package com.noahhendrickson.gateway.earning;

import com.noahhendrickson.kernel.earning.MessageEarningApplicationEvent;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class MessageEarningListener extends ListenerAdapter {

    private final EarningEventFactory factory;
    private final ApplicationEventPublisher eventPublisher;

    public MessageEarningListener(EarningEventFactory factory, ApplicationEventPublisher eventPublisher) {
        this.factory = factory;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.isWebhookMessage()) return;
        if (!event.isFromGuild()) return;

        Message message = event.getMessage();
        eventPublisher.publishEvent(new MessageEarningApplicationEvent(
                factory.buildMessageEvent(event.getAuthor(), event.getGuild(), event.getMember(),
                        message.getContentRaw().length(), !message.getAttachments().isEmpty())
        ));
    }
}
