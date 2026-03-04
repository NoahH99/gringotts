package com.noahhendrickson.persistence.earning;

import com.noahhendrickson.kernel.earning.EarningEvent;
import com.noahhendrickson.kernel.earning.EarningSource;
import com.noahhendrickson.kernel.earning.MessageEarningEvent;
import com.noahhendrickson.persistence.entity.GuildEconomyConfigEntity;
import org.springframework.stereotype.Component;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class MessageEarningStrategy implements EarningStrategy {

    @Override
    public EarningSource source() {
        return EarningSource.MESSAGE;
    }

    @Override
    public int cooldownSecs(GuildEconomyConfigEntity config) {
        return config.getTextCooldownSecs();
    }

    @Override
    public boolean isEligible(EarningEvent event, GuildEconomyConfigEntity config) {
        if (!(event instanceof MessageEarningEvent e))
            throw new IllegalArgumentException("Expected MessageEarningEvent, got: " + event.getClass());
        return e.hasAttachment() || e.contentLength() > 12;
    }

    @Override
    public long computeAmount(EarningEvent event, GuildEconomyConfigEntity config) {
        if (ThreadLocalRandom.current().nextDouble() >= config.getTextAwardChance().doubleValue()) return 0;
        return ThreadLocalRandom.current().nextLong(config.getTextAwardMin(), (long) config.getTextAwardMax() + 1);
    }

    @Override
    public String eventType() {
        return "EARNING_MESSAGE";
    }
}
