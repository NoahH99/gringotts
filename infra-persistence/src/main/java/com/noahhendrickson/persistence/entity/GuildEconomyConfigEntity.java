package com.noahhendrickson.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
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
    private int textAwardMin;

    @Column(name = "text_award_max", nullable = false)
    private int textAwardMax;

    @Column(name = "text_award_chance", nullable = false, precision = 5, scale = 4)
    private BigDecimal textAwardChance;

    @Column(name = "text_cooldown_secs", nullable = false)
    private int textCooldownSecs;

    @Column(name = "voice_tick_secs", nullable = false)
    private int voiceTickSecs;

    @Column(name = "voice_award_per_tick", nullable = false)
    private int voiceAwardPerTick;

    @Column(name = "voice_cooldown_secs", nullable = false)
    private int voiceCooldownSecs;

    @Column(name = "deposit_fee_bps", nullable = false)
    private int depositFeeBps;

    @Column(name = "withdraw_fee_bps", nullable = false)
    private int withdrawFeeBps;

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

    public int getVoiceAwardPerTick() {
        return voiceAwardPerTick;
    }

    public void setVoiceAwardPerTick(int voiceAwardPerTick) {
        this.voiceAwardPerTick = voiceAwardPerTick;
    }

    public int getVoiceCooldownSecs() {
        return voiceCooldownSecs;
    }

    public void setVoiceCooldownSecs(int voiceCooldownSecs) {
        this.voiceCooldownSecs = voiceCooldownSecs;
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
