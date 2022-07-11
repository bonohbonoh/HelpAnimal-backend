package com.jeonggolee.helpanimal.domain.crew.repository;

import com.jeonggolee.helpanimal.domain.crew.domain.Crews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CrewRepository extends JpaRepository<Crews, Long>, JpaSpecificationExecutor<Crews> {
    boolean existsCrewByName(String name);
}
