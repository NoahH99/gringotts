package com.noahhendrickson.persistence.earning;

import com.noahhendrickson.kernel.earning.EarningEvent;
import com.noahhendrickson.kernel.earning.MessageEarningApplicationEvent;
import com.noahhendrickson.kernel.earning.VoiceEarningApplicationEvent;
import com.noahhendrickson.ledger.earning.EarningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EarningEventHandler {

    private static final Logger log = LoggerFactory.getLogger(EarningEventHandler.class);

    private final EarningService earningService;

    public EarningEventHandler(EarningService earningService) {
        this.earningService = earningService;
    }

    @Async("earningExecutor")
    @EventListener
    public void onMessageEarning(MessageEarningApplicationEvent event) {
        handle(event.earningEvent());
    }

    @Async("earningExecutor")
    @EventListener
    public void onVoiceEarning(VoiceEarningApplicationEvent event) {
        handle(event.earningEvent());
    }

    private void handle(EarningEvent event) {
        try {
            earningService.tryAward(event);
        } catch (Exception e) {
            log.error("Failed to process earning event for source {}", event.source(), e);
        }
    }
}
