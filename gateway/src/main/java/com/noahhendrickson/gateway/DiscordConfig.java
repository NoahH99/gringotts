package com.noahhendrickson.gateway;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
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
        JDABuilder builder = JDABuilder.createDefault(token);
        listeners.forEach(builder::addEventListeners);
        return builder.build().awaitReady();
    }
}
