package com.jeonggolee.helpanimal.domain.crew.repository;

import com.jeonggolee.helpanimal.domain.crew.domain.Crew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CrewRepository extends JpaRepository<Crew, Long>, JpaSpecificationExecutor<Crew> {
    boolean existsCrewByName(String name);
}
