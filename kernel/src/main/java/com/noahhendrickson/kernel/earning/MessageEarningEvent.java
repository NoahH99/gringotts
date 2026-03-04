package com.noahhendrickson.kernel.earning;

import com.noahhendrickson.kernel.sync.DiscordEntitySnapshot;

public record MessageEarningEvent(
        DiscordEntitySnapshot snapshot,
        int contentLength,
        boolean hasAttachment
) implements EarningEvent {

    @Override
    public EarningSource source() {
        return EarningSource.MESSAGE;
    }
}
