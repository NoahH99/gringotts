package com.noahhendrickson.gateway.presence;

import com.noahhendrickson.kernel.bot.BotActivity;
import com.noahhendrickson.kernel.bot.BotPresenceService;
import com.noahhendrickson.kernel.bot.BotStatus;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("NullableProblems") // ObjectProvider<JDA>: getIfAvailable() is @Nullable by design
public class JdaBotPresenceService implements BotPresenceService {

    private final ObjectProvider<JDA> jdaProvider;

    public JdaBotPresenceService(ObjectProvider<JDA> jdaProvider) {
        this.jdaProvider = jdaProvider;
    }

    @Override
    public void setPresence(BotStatus status, BotActivity activity) {
        JDA jda = jdaProvider.getIfAvailable();
        if (jda == null) return;
        jda.getPresence().setPresence(onlineStatus(status), jdaActivity(activity));
    }

    private OnlineStatus onlineStatus(BotStatus status) {
        return switch (status) {
            case STARTING, SYNCING, MAINTENANCE -> OnlineStatus.DO_NOT_DISTURB;
            case READY -> OnlineStatus.ONLINE;
        };
    }

    private Activity jdaActivity(BotActivity activity) {
        return switch (activity.type()) {
            case PLAYING    -> Activity.playing(activity.text());
            case WATCHING   -> Activity.watching(activity.text());
            case LISTENING  -> Activity.listening(activity.text());
            case COMPETING  -> Activity.competing(activity.text());
        };
    }
}
