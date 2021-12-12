package com.jeonggolee.helpanimal.domain.crew.repository;

import com.jeonggolee.helpanimal.domain.crew.domain.Crew;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;//예외 테스트시 사용


//@Transactional 사용시 테스트 하나 끝날때마다 롤백이 일어남. (테스트의 원자성)
@Transactional
@SpringBootTest
class CrewRepositoryTest {
    @Autowired CrewRepository crewRepository;

    @Test
    @DisplayName("크루 생성")
    void saveCrewTest(){
        //given
        Crew crew = Crew.builder().name("테스트크루").build();

        //when
        Crew saveCrew = crewRepository.save(crew);

        //then
        assertThat(saveCrew.getName()).isEqualTo("테스트크루");
    }

    @Test
    @DisplayName("크루 조회")
    void findCrewTest(){
        //given
        Crew crew = Crew.builder().name("테스트크루").build();

        //when
        Long saveId = crewRepository.save(crew).getId();

        //then
        Crew findCrew = crewRepository.findById(saveId).get();
        assertThat(findCrew.getName()).isEqualTo("테스트크루");
    }

    @Test
    @DisplayName("크루 수정")
    void updateCrewTest(){
        //given
        Crew crew = Crew.builder().name("테스트크루").build();

        //when
        Long saveId = crewRepository.save(crew).getId();

        Crew findCrew = crewRepository.findById(saveId).get();
        findCrew.setName("바꾼크루");

        crewRepository.save(findCrew);

        //then
        Crew updateCrew = crewRepository.findById(saveId).get();
        assertThat(updateCrew.getName()).isEqualTo("바꾼크루");
    }

    @Test
    @DisplayName("크루 삭제")
    void deleteCrewTest(){
        //given
        Crew crew = Crew.builder().name("테스트크루").build();

        //when
        Long saveId = crewRepository.save(crew).getId();
        crewRepository.deleteById(saveId);

        //then
        Optional<Crew> deleteCrew = crewRepository.findById(saveId);
        assertThat(deleteCrew.isPresent()).isFalse();
    }


}