package com.noahhendrickson.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EarningCooldownId implements Serializable {

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "source", nullable = false, columnDefinition = "earning_source")
    private EarningSource source;

    public EarningCooldownId() {
    }

    public EarningCooldownId(Long memberId, EarningSource source) {
        this.memberId = memberId;
        this.source = source;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public EarningSource getSource() {
        return source;
    }

    public void setSource(EarningSource source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EarningCooldownId that)) return false;
        return Objects.equals(memberId, that.memberId) && source == that.source;
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, source);
    }
}
