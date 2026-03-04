package com.noahhendrickson.kernel.sync;

import java.time.OffsetDateTime;

public record DiscordEntitySnapshot(
        long userId,
        String username,
        String globalName,
        String avatarHash,
        long guildId,
        String guildName,
        String guildIconHash,
        Long guildOwnerId,
        OffsetDateTime memberJoinedAt
) {
}
