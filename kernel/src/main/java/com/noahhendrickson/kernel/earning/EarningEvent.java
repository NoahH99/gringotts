package com.noahhendrickson.kernel.earning;

import com.noahhendrickson.kernel.sync.DiscordEntitySnapshot;

public sealed interface EarningEvent permits MessageEarningEvent, VoiceEarningEvent {
    DiscordEntitySnapshot snapshot();
    EarningSource source();
}
