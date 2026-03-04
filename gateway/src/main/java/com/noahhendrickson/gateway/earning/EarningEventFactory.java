package com.noahhendrickson.gateway.earning;

import com.noahhendrickson.kernel.earning.MessageEarningEvent;
import com.noahhendrickson.kernel.earning.VoiceEarningEvent;
import com.noahhendrickson.kernel.sync.DiscordEntitySnapshot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class EarningEventFactory {

    public MessageEarningEvent buildMessageEvent(User user, Guild guild, Member member, int contentLength, boolean hasAttachment) {
        return new MessageEarningEvent(buildSnapshot(user, guild, member), contentLength, hasAttachment);
    }

    public VoiceEarningEvent buildVoiceEvent(User user, Guild guild, Member member, long sessionDurationSecs) {
        return new VoiceEarningEvent(buildSnapshot(user, guild, member), sessionDurationSecs);
    }

    private DiscordEntitySnapshot buildSnapshot(User user, Guild guild, Member member) {
        OffsetDateTime memberJoinedAt = member != null ? member.getTimeJoined() : null;
        return new DiscordEntitySnapshot(
                user.getIdLong(),
                user.getName(),
                user.getGlobalName(),
                user.getAvatarId(),
                guild.getIdLong(),
                guild.getName(),
                guild.getIconId(),
                guild.getOwnerIdLong(),
                memberJoinedAt
        );
    }
}
