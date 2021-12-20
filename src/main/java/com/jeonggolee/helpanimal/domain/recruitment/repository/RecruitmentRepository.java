package com.jeonggolee.helpanimal.domain.recruitment.repository;

import com.jeonggolee.helpanimal.domain.recruitment.entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
    public Optional<Recruitment> findByName(String name);
}
