package com.jeonggolee.helpanimal.domain.crew.repository;

import com.jeonggolee.helpanimal.domain.crew.domain.Crews;
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
class CrewsRepositoryTest {
    @Autowired
    CrewRepository crewRepository;

    @Autowired
    CrewSpecification cs;

    @Test
    @DisplayName("크루 생성")
    void saveCrewTest(){
        //given
        Crews crews = Crews.builder().name("테스트크루").crewMembersList(new ArrayList<>()).build();

        //when
        Crews saveCrews = crewRepository.save(crews);

        //then
        assertThat(saveCrews.getName()).isEqualTo("테스트크루");
    }

    @Test
    @DisplayName("크루 아이디로 조회")
    void findCrewTest(){
        //given
        Crews crews = Crews.builder().name("테스트크루").crewMembersList(new ArrayList<>()).build();

        //when
        Long saveId = crewRepository.save(crews).getId();

        //then
        Crews findCrews = crewRepository.findById(saveId).get();
        assertThat(findCrews.getName()).isEqualTo("테스트크루");
    }

    @Test
    @DisplayName("크루 아이디로 조회 실패")
    void findCrewFailTest(){
        //given

        //when
        Long wrongId = 0L;

        //then
        Optional<Crews> findCrew = crewRepository.findById(wrongId);
        assertThat(findCrew.isPresent()).isFalse();
    }

    @Test
    @DisplayName("크루 이름으로 조회")
    void findCrewWithNameTest(){
        //given
        Crews crews = Crews.builder().name("테스트크루").crewMembersList(new ArrayList<>()).build();

        //when
        crewRepository.save(crews);

        //then
        Optional<Crews> findCrew = crewRepository.findOne(cs.searchWithName("테스트크루"));
        assertThat(findCrew.isPresent()).isTrue();
    }

    @Test
    @DisplayName("크루 이름으로 조회 실패")
    void findCrewWithNameFailTest(){
        //given
        Crews crews = Crews.builder().name("테스트크루").crewMembersList(new ArrayList<>()).build();

        //when
        crewRepository.save(crews);

        //then
        Optional<Crews> findCrew = crewRepository.findOne(cs.searchWithName("틀린크루이름"));
        assertThat(findCrew.isPresent()).isFalse();
    }

    @Test
    @DisplayName("크루 수정")
    void updateCrewTest(){
        //given
        Crews crews = Crews.builder().name("테스트크루").crewMembersList(new ArrayList<>()).build();

        //when
        Long saveId = crewRepository.save(crews).getId();

        Crews findCrews = crewRepository.findById(saveId).get();
        findCrews.updateName("바꾼크루");

        crewRepository.save(findCrews);

        //then
        Crews updateCrews = crewRepository.findById(saveId).get();
        assertThat(updateCrews.getName()).isEqualTo("바꾼크루");
    }

    @Test
    @DisplayName("크루 삭제")
    void deleteCrewTest(){
        //given
        Crews crews = Crews.builder().name("테스트크루").crewMembersList(new ArrayList<>()).build();

        //when
        Crews saveCrews = crewRepository.save(crews);
        saveCrews.delete();

        Long saveId = crewRepository.save(saveCrews).getId();

        //then
        Optional<Crews> deleteCrew = crewRepository.findOne(cs.searchWithId(saveId));
        assertThat(deleteCrew.isPresent()).isFalse();
    }


}