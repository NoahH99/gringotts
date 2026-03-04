package com.noahhendrickson.persistence.sync;

import com.noahhendrickson.kernel.sync.DiscordEntitySnapshot;
import com.noahhendrickson.persistence.entity.GuildEntity;
import com.noahhendrickson.persistence.entity.GuildMemberEntity;
import com.noahhendrickson.persistence.entity.UserEntity;
import com.noahhendrickson.persistence.repository.GuildMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;

@Service
@Transactional
public class GuildMemberSyncService {

    private final GuildMemberRepository memberRepository;

    public GuildMemberSyncService(GuildMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public GuildMemberEntity sync(UserEntity user, GuildEntity guild, DiscordEntitySnapshot snapshot) {
        return memberRepository.findByGuildIdAndUserId(guild.getId(), user.getId())
                .map(member -> {
                    if (member.getJoinedAt() == null && snapshot.memberJoinedAt() != null) {
                        member.setJoinedAt(snapshot.memberJoinedAt());
                        return memberRepository.save(member);
                    }
                    return member;
                })
                .orElseGet(() -> {
                    GuildMemberEntity member = new GuildMemberEntity();
                    member.setGuild(guild);
                    member.setUser(user);
                    OffsetDateTime joinedAt = snapshot.memberJoinedAt();
                    if (joinedAt != null) {
                        member.setJoinedAt(joinedAt);
                    }
                    return memberRepository.save(member);
                });
    }
}
