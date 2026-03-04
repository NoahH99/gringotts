package com.noahhendrickson.persistence.repository;

import com.noahhendrickson.persistence.entity.GuildMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuildMemberRepository extends JpaRepository<GuildMemberEntity, Long> {

    Optional<GuildMemberEntity> findByGuildIdAndUserId(long guildId, long userId);
}
