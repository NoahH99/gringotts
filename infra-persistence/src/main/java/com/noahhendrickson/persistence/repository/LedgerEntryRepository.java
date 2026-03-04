package com.noahhendrickson.persistence.repository;

import com.noahhendrickson.persistence.entity.LedgerEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerEntryRepository extends JpaRepository<LedgerEntryEntity, Long> {
}
