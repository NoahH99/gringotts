package com.noahhendrickson.persistence.repository;

import com.noahhendrickson.persistence.entity.EarningCooldownEntity;
import com.noahhendrickson.persistence.entity.EarningCooldownId;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EarningCooldownRepository extends JpaRepository<EarningCooldownEntity, EarningCooldownId> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM EarningCooldownEntity e WHERE e.id = :id")
    Optional<EarningCooldownEntity> findByIdForUpdate(@Param("id") EarningCooldownId id);
}
