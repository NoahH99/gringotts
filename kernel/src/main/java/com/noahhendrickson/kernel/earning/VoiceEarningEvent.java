package com.noahhendrickson.kernel.earning;

import com.noahhendrickson.kernel.sync.DiscordEntitySnapshot;

public record VoiceEarningEvent(
        DiscordEntitySnapshot snapshot,
        long sessionDurationSecs
) implements EarningEvent {

    @Override
    public EarningSource source() {
        return EarningSource.VOICE;
    }
}
