package com.jeonggolee.helpanimal.domain.user.repository;

import com.jeonggolee.helpanimal.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> , JpaSpecificationExecutor<User> {
    Optional<User> findByEmailAndDeletedAtNull(String email);
}
