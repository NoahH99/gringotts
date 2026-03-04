package com.noahhendrickson.gateway.config;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConditionalOnExpression("!'${discord.token:}'.isBlank()")
public class DiscordConfig {

    private static final Logger log = LoggerFactory.getLogger(DiscordConfig.class);

    @Bean
    public JDA jda(@Value("${discord.token}") String token,
                   List<ListenerAdapter> listeners) {
        JDABuilder builder = JDABuilder.createLight(token)
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setActivity(Activity.playing("Starting up..."))
                .enableIntents(
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_VOICE_STATES
                );

        registerListeners(builder, listeners);
        return builder.build();
    }

    private void registerListeners(JDABuilder builder, List<ListenerAdapter> listeners) {
        listeners.forEach(listener -> {
            builder.addEventListeners(listener);
            log.debug("Registered listener: {}", listener.getClass().getSimpleName());
        });
    }
}
