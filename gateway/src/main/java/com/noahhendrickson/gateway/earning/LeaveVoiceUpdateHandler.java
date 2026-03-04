package com.noahhendrickson.gateway.earning;

import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import org.springframework.stereotype.Component;

@Component
public class LeaveVoiceUpdateHandler implements VoiceUpdateHandler {

    private final VoiceChannelEvaluator evaluator;

    public LeaveVoiceUpdateHandler(VoiceChannelEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public boolean supports(AudioChannel left, AudioChannel joined) {
        return joined == null;
    }

    @Override
    public void handle(GuildVoiceUpdateEvent event) {
        evaluator.removeUser(event.getMember().getUser().getIdLong(), event.getGuild().getIdLong());
        evaluator.evaluate(event.getChannelLeft(), event.getGuild());
    }
}
