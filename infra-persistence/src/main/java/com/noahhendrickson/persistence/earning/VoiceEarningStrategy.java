package com.noahhendrickson.persistence.earning;

import com.noahhendrickson.kernel.earning.EarningEvent;
import com.noahhendrickson.kernel.earning.EarningSource;
import com.noahhendrickson.kernel.earning.VoiceEarningEvent;
import com.noahhendrickson.persistence.entity.GuildEconomyConfigEntity;
import org.springframework.stereotype.Component;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class VoiceEarningStrategy implements EarningStrategy {

    @Override
    public EarningSource source() {
        return EarningSource.VOICE;
    }

    @Override
    public int cooldownSecs(GuildEconomyConfigEntity config) {
        return config.getVoiceTickSecs();
    }

    @Override
    public boolean isEligible(EarningEvent event, GuildEconomyConfigEntity config) {
        if (!(event instanceof VoiceEarningEvent e))
            throw new IllegalArgumentException("Expected VoiceEarningEvent, got: " + event.getClass());
        return e.sessionDurationSecs() >= config.getVoiceMinDurationSecs();
    }

    @Override
    public long computeAmount(EarningEvent event, GuildEconomyConfigEntity config) {
        return ThreadLocalRandom.current().nextLong(config.getVoiceAwardMin(), (long) config.getVoiceAwardMax() + 1);
    }

    @Override
    public String eventType() {
        return "EARNING_VOICE";
    }
}
