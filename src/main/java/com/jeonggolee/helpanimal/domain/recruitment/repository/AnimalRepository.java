package com.jeonggolee.helpanimal.domain.recruitment.repository;

import com.jeonggolee.helpanimal.domain.recruitment.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal,Long> {
    Optional<Animal> findByName(String name);
    Optional<Animal> findByNameAndDeletedAtNull(String name);
}
