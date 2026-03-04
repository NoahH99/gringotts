package com.noahhendrickson.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "earning_cooldowns")
public class EarningCooldownEntity {

    @EmbeddedId
    private EarningCooldownId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId")
    @JoinColumn(name = "member_id", nullable = false)
    private GuildMemberEntity member;

    @Column(name = "last_awarded_at", nullable = false)
    private OffsetDateTime lastAwardedAt;

    public EarningCooldownId getId() {
        return id;
    }

    public void setId(EarningCooldownId id) {
        this.id = id;
    }

    public GuildMemberEntity getMember() {
        return member;
    }

    public void setMember(GuildMemberEntity member) {
        this.member = member;
    }

    public OffsetDateTime getLastAwardedAt() {
        return lastAwardedAt;
    }

    public void setLastAwardedAt(OffsetDateTime lastAwardedAt) {
        this.lastAwardedAt = lastAwardedAt;
    }
}
