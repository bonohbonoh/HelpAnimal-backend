package com.jeonggolee.helpanimal.domain.crew.repository;

import com.jeonggolee.helpanimal.domain.crew.domain.CrewMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {
}
