package com.noahhendrickson.gateway.earning;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VoiceSessionStartupSync extends ListenerAdapter {

    private static final Logger log = LoggerFactory.getLogger(VoiceSessionStartupSync.class);
    private final VoiceChannelEvaluator evaluator;

    public VoiceSessionStartupSync(VoiceChannelEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public void onReady(ReadyEvent event) {
        for (Guild guild : event.getJDA().getGuilds()) {
            for (AudioChannel channel : guild.getVoiceChannels()) {
                evaluator.evaluate(channel, guild);
            }
        }
    }
}
