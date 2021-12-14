package com.jeonggolee.helpanimal.domain.crew.repository;

import com.jeonggolee.helpanimal.domain.crew.domain.Crew;
import com.jeonggolee.helpanimal.domain.crew.query.CrewSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


//@Transactional 사용시 테스트 하나 끝날때마다 롤백이 일어남. (테스트의 원자성)
@Transactional
@SpringBootTest
class CrewRepositoryTest {
    @Autowired
    CrewRepository crewRepository;

    @Autowired
    CrewSpecification cs;

    @Test
    @DisplayName("크루 생성")
    void saveCrewTest(){
        //given
        Crew crew = Crew.builder().name("테스트크루").crewMemberList(new ArrayList<>()).build();

        //when
        Crew saveCrew = crewRepository.save(crew);

        //then
        assertThat(saveCrew.getName()).isEqualTo("테스트크루");
    }

    @Test
    @DisplayName("크루 아이디로 조회")
    void findCrewTest(){
        //given
        Crew crew = Crew.builder().name("테스트크루").crewMemberList(new ArrayList<>()).build();

        //when
        Long saveId = crewRepository.save(crew).getId();

        //then
        Crew findCrew = crewRepository.findById(saveId).get();
        assertThat(findCrew.getName()).isEqualTo("테스트크루");
    }

    @Test
    @DisplayName("크루 아이디로 조회 실패")
    void findCrewFailTest(){
        //given

        //when
        Long wrongId = 0L;

        //then
        Optional<Crew> findCrew = crewRepository.findById(wrongId);
        assertThat(findCrew.isPresent()).isFalse();
    }

    @Test
    @DisplayName("크루 이름으로 조회")
    void findCrewWithNameTest(){
        //given
        Crew crew = Crew.builder().name("테스트크루").crewMemberList(new ArrayList<>()).build();

        //when
        crewRepository.save(crew);

        //then
        Optional<Crew> findCrew = crewRepository.findOne(cs.searchWithName("테스트크루"));
        assertThat(findCrew.isPresent()).isTrue();
    }

    @Test
    @DisplayName("크루 이름으로 조회 실패")
    void findCrewWithNameFailTest(){
        //given
        Crew crew = Crew.builder().name("테스트크루").crewMemberList(new ArrayList<>()).build();

        //when
        crewRepository.save(crew);

        //then
        Optional<Crew> findCrew = crewRepository.findOne(cs.searchWithName("틀린크루이름"));
        assertThat(findCrew.isPresent()).isFalse();
    }

    @Test
    @DisplayName("크루 수정")
    void updateCrewTest(){
        //given
        Crew crew = Crew.builder().name("테스트크루").crewMemberList(new ArrayList<>()).build();

        //when
        Long saveId = crewRepository.save(crew).getId();

        Crew findCrew = crewRepository.findById(saveId).get();
        findCrew.updateName("바꾼크루");

        crewRepository.save(findCrew);

        //then
        Crew updateCrew = crewRepository.findById(saveId).get();
        assertThat(updateCrew.getName()).isEqualTo("바꾼크루");
    }

    @Test
    @DisplayName("크루 삭제")
    void deleteCrewTest(){
        //given
        Crew crew = Crew.builder().name("테스트크루").crewMemberList(new ArrayList<>()).build();

        //when
        Crew saveCrew = crewRepository.save(crew);
        saveCrew.delete();

        Long saveId = crewRepository.save(saveCrew).getId();

        //then
        Optional<Crew> deleteCrew = crewRepository.findOne(cs.searchWithId(saveId));
        assertThat(deleteCrew.isPresent()).isFalse();
    }


}