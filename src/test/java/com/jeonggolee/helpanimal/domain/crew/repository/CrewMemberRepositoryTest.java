package com.jeonggolee.helpanimal.domain.crew.repository;

import com.jeonggolee.helpanimal.domain.crew.domain.Crew;
import com.jeonggolee.helpanimal.domain.crew.domain.CrewMember;
import com.jeonggolee.helpanimal.domain.crew.enums.CrewMemberRole;
import com.jeonggolee.helpanimal.domain.crew.query.CrewMemberSpecification;
import com.jeonggolee.helpanimal.domain.user.entity.User;
import com.jeonggolee.helpanimal.domain.user.repository.UserRepository;
import com.jeonggolee.helpanimal.domain.user.util.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class CrewMemberRepositoryTest {
    @Autowired
    CrewRepository crewRepository;

    @Autowired
    CrewMemberRepository crewMemberRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CrewMemberSpecification cms;


    private User saveUser(String email){
        User user = User.builder()
                .email(email)
                .password("테스트비밀번호")
                .name("테스트이름")
                .nickname("테스트별명")
                .profileImage("https://img.url.test")
                .role(Role.USER)
                .build();

        return userRepository.save(user);
    }

    private Crew saveCrew(String name){
        Crew crew = Crew.builder().name(name).crewMemberList(new ArrayList<>()).build();

        return crewRepository.save(crew);
    }

    private CrewMember generateCrewMember(User user, Crew crew){
        CrewMember crewMember = CrewMember.builder().role(CrewMemberRole.MASTER).build();

        crew.addCrewMember(crewMember);
        crewRepository.save(crew);

        crewMember.registerUser(user);

        return crewMember;
    }

    @Test
    @DisplayName("크루 맴버 생성")
    void saveCrewMemberTest(){
        //given
        User user = saveUser("테스트이메일");
        Crew crew = saveCrew("테스트크루");
        CrewMember crewMember = generateCrewMember(user, crew);

        //when
        CrewMember saveCrewMember = crewMemberRepository.save(crewMember);

        //then
        assertThat(saveCrewMember.getCrew().getName()).isEqualTo("테스트크루");
    }

    @Test
    @DisplayName("크루 맴버 아이디로 조회")
    void findCrewMemberTest(){
        //given
        User user = saveUser("테스트이메일");
        Crew crew = saveCrew("테스트크루");
        CrewMember crewMember = generateCrewMember(user, crew);

        //when
        Long saveId = crewMemberRepository.save(crewMember).getId();

        //then
        CrewMember findCrewMember = crewMemberRepository.findById(saveId).get();
        assertThat(findCrewMember.getCrew().getName()).isEqualTo("테스트크루");
    }

    @Test
    @DisplayName("크루 맴버 아이디로 조회 실패")
    void findCrewMemberFailTest(){
        //given

        //when
        Long saveId = 0L;

        //then
        Optional<CrewMember> findCrewMember = crewMemberRepository.findById(saveId);
        assertThat(findCrewMember.isPresent()).isFalse();
    }

    @Test
    @DisplayName("크루맴버 삭제")
    void deleteCrewMemberTest(){
        //given
        User user = saveUser("테스트이메일");
        Crew crew = saveCrew("테스트크루");
        CrewMember crewMember = generateCrewMember(user, crew);
        crewMemberRepository.save(crewMember);

        //when
        crew.removeCrewMember(crewMember);
        crewRepository.save(crew);

        Long saveId = crewMemberRepository.save(crewMember).getId();

        //then
        Optional<CrewMember> deleteCrewMember = crewMemberRepository.findOne(cms.searchWithId(saveId));
        assertThat(deleteCrewMember.isPresent()).isFalse();

    }


}