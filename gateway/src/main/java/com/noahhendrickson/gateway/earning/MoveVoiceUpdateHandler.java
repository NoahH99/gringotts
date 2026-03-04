package com.noahhendrickson.gateway.earning;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import org.springframework.stereotype.Component;

@Component
public class MoveVoiceUpdateHandler implements VoiceUpdateHandler {

    private final VoiceChannelEvaluator evaluator;

    public MoveVoiceUpdateHandler(VoiceChannelEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public boolean supports(AudioChannel left, AudioChannel joined) {
        return left != null && joined != null;
    }

    @Override
    public void handle(GuildVoiceUpdateEvent event) {
        AudioChannel joined = event.getChannelJoined();
        Guild guild = event.getGuild();

        if (evaluator.isAfkChannel(joined, guild)) {
            evaluator.removeUser(event.getMember().getUser().getIdLong(), guild.getIdLong());
        }

        evaluator.evaluate(event.getChannelLeft(), guild);
        evaluator.evaluate(joined, guild);
    }
}
