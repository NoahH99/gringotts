package com.noahhendrickson.persistence.repository;

import com.noahhendrickson.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
