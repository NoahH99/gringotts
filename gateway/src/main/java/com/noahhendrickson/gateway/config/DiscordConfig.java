package com.noahhendrickson.gateway.config;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
@ConditionalOnExpression("!'${discord.token:}'.isBlank()")
public class DiscordConfig {

    @Bean
    public JDA jda(@Value("${discord.token}") String token,
                   List<ListenerAdapter> listeners) throws InterruptedException {
        JDABuilder builder = JDABuilder.createDefault(token)
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setActivity(Activity.playing("Starting up..."));
        listeners.forEach(builder::addEventListeners);
        return builder.build().awaitReady();
    }
}
