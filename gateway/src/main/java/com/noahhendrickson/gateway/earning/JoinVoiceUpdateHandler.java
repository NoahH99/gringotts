package com.noahhendrickson.gateway.earning;

import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import org.springframework.stereotype.Component;

@Component
public class JoinVoiceUpdateHandler implements VoiceUpdateHandler {

    private final VoiceChannelEvaluator evaluator;

    public JoinVoiceUpdateHandler(VoiceChannelEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public boolean supports(AudioChannel left, AudioChannel joined) {
        return left == null;
    }

    @Override
    public void handle(GuildVoiceUpdateEvent event) {
        evaluator.evaluate(event.getChannelJoined(), event.getGuild());
    }
}
