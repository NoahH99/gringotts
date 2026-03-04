package com.noahhendrickson.persistence.repository;

import com.noahhendrickson.persistence.entity.GuildEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuildRepository extends JpaRepository<GuildEntity, Long> {
}
