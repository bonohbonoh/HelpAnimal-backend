package com.jeonggolee.helpanimal.domain.user.repository;

import com.jeonggolee.helpanimal.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
