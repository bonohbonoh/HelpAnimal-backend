package com.jeonggolee.helpanimal.domain.user.repository;

import com.jeonggolee.helpanimal.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> , JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByEmailAndDeletedAtNull(String email);
}
