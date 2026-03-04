package com.noahhendrickson.persistence.repository;

import com.noahhendrickson.persistence.entity.AccountEntity;
import com.noahhendrickson.persistence.entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByMemberIdAndAccountType(long memberId, AccountType accountType);
}
