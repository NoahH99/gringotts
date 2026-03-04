package com.noahhendrickson.persistence.earning;

import com.noahhendrickson.kernel.earning.EarningEvent;
import com.noahhendrickson.kernel.earning.EarningSource;
import com.noahhendrickson.persistence.entity.GuildEconomyConfigEntity;

public interface EarningStrategy {

    EarningSource source();

    int cooldownSecs(GuildEconomyConfigEntity config);

    boolean isEligible(EarningEvent event, GuildEconomyConfigEntity config);

    long computeAmount(EarningEvent event, GuildEconomyConfigEntity config);

    String eventType();
}
