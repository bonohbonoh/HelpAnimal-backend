package com.jeonggolee.helpanimal.domain.crew.repository;

import com.jeonggolee.helpanimal.domain.crew.domain.CrewMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CrewMemberRepository extends JpaRepository<CrewMembers, Long>, JpaSpecificationExecutor<CrewMembers> {
}
