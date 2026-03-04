package com.noahhendrickson.gateway.earning;

import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;

public interface VoiceUpdateHandler {

    boolean supports(AudioChannel left, AudioChannel joined);

    void handle(GuildVoiceUpdateEvent event);
}
