package com.noahhendrickson.persistence.repository;

import com.noahhendrickson.persistence.entity.GuildEconomyConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuildEconomyConfigRepository extends JpaRepository<GuildEconomyConfigEntity, Long> {
}
