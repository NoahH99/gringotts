package com.noahhendrickson.gateway.earning;

import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceDeafenEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceSelfDeafenEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoiceEarningListener extends ListenerAdapter {

    private final List<VoiceUpdateHandler> handlers;
    private final VoiceChannelEvaluator evaluator;

    public VoiceEarningListener(List<VoiceUpdateHandler> handlers, VoiceChannelEvaluator evaluator) {
        this.handlers = handlers;
        this.evaluator = evaluator;
    }

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        AudioChannel left = event.getChannelLeft();
        AudioChannel joined = event.getChannelJoined();

        handlers.stream()
                .filter(h -> h.supports(left, joined))
                .findFirst()
                .ifPresent(h -> h.handle(event));
    }

    @Override
    public void onGuildVoiceDeafen(GuildVoiceDeafenEvent event) {
        AudioChannel channel = event.getVoiceState().getChannel();
        if (channel != null) {
            evaluator.evaluate(channel, event.getGuild());
        }
    }

    @Override
    public void onGuildVoiceSelfDeafen(GuildVoiceSelfDeafenEvent event) {
        AudioChannel channel = event.getVoiceState().getChannel();
        if (channel != null) {
            evaluator.evaluate(channel, event.getGuild());
        }
    }
}
