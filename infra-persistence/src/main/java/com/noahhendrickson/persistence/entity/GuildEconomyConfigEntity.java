package com.noahhendrickson.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "guild_economy_config")
public class GuildEconomyConfigEntity {

    @Id
    private Long guildId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "guild_id")
    private GuildEntity guild;

    @Column(name = "text_award_min", nullable = false)
    private int textAwardMin = 3;

    @Column(name = "text_award_max", nullable = false)
    private int textAwardMax = 10;

    @Column(name = "text_award_chance", nullable = false, precision = 5, scale = 4)
    private BigDecimal textAwardChance = new BigDecimal("0.7500");

    @Column(name = "text_cooldown_secs", nullable = false)
    private int textCooldownSecs = 60;

    @Column(name = "voice_tick_secs", nullable = false)
    private int voiceTickSecs = 120;

    @Column(name = "voice_award_min", nullable = false)
    private int voiceAwardMin = 1;

    @Column(name = "voice_award_max", nullable = false)
    private int voiceAwardMax = 2;

    @Column(name = "voice_min_duration_secs", nullable = false)
    private int voiceMinDurationSecs = 600;

    @Column(name = "deposit_fee_bps", nullable = false)
    private int depositFeeBps = 0;

    @Column(name = "withdraw_fee_bps", nullable = false)
    private int withdrawFeeBps = 0;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    void prePersist() {
        OffsetDateTime now = OffsetDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    void preUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    public Long getGuildId() {
        return guildId;
    }

    public void setGuildId(Long guildId) {
        this.guildId = guildId;
    }

    public GuildEntity getGuild() {
        return guild;
    }

    public void setGuild(GuildEntity guild) {
        this.guild = guild;
    }

    public int getTextAwardMin() {
        return textAwardMin;
    }

    public void setTextAwardMin(int textAwardMin) {
        this.textAwardMin = textAwardMin;
    }

    public int getTextAwardMax() {
        return textAwardMax;
    }

    public void setTextAwardMax(int textAwardMax) {
        this.textAwardMax = textAwardMax;
    }

    public BigDecimal getTextAwardChance() {
        return textAwardChance;
    }

    public void setTextAwardChance(BigDecimal textAwardChance) {
        this.textAwardChance = textAwardChance;
    }

    public int getTextCooldownSecs() {
        return textCooldownSecs;
    }

    public void setTextCooldownSecs(int textCooldownSecs) {
        this.textCooldownSecs = textCooldownSecs;
    }

    public int getVoiceTickSecs() {
        return voiceTickSecs;
    }

    public void setVoiceTickSecs(int voiceTickSecs) {
        this.voiceTickSecs = voiceTickSecs;
    }

    public int getVoiceAwardMin() {
        return voiceAwardMin;
    }

    public void setVoiceAwardMin(int voiceAwardMin) {
        this.voiceAwardMin = voiceAwardMin;
    }

    public int getVoiceAwardMax() {
        return voiceAwardMax;
    }

    public void setVoiceAwardMax(int voiceAwardMax) {
        this.voiceAwardMax = voiceAwardMax;
    }

    public int getVoiceMinDurationSecs() {
        return voiceMinDurationSecs;
    }

    public void setVoiceMinDurationSecs(int voiceMinDurationSecs) {
        this.voiceMinDurationSecs = voiceMinDurationSecs;
    }

    public int getDepositFeeBps() {
        return depositFeeBps;
    }

    public void setDepositFeeBps(int depositFeeBps) {
        this.depositFeeBps = depositFeeBps;
    }

    public int getWithdrawFeeBps() {
        return withdrawFeeBps;
    }

    public void setWithdrawFeeBps(int withdrawFeeBps) {
        this.withdrawFeeBps = withdrawFeeBps;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
