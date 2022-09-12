package com.jeonggolee.helpanimal.domain.crew.repository;

import com.jeonggolee.helpanimal.domain.crew.domain.Crews;
import com.jeonggolee.helpanimal.domain.crew.domain.CrewMembers;
import com.jeonggolee.helpanimal.domain.crew.enums.CrewMemberRole;
import com.jeonggolee.helpanimal.domain.crew.query.CrewMemberSpecification;
import com.jeonggolee.helpanimal.domain.user.entity.UserEntity;
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
class CrewsMemberRepositoryTest {
    @Autowired
    CrewRepository crewRepository;

    @Autowired
    CrewMemberRepository crewMemberRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CrewMemberSpecification cms;


    private UserEntity saveUser(String email){
        UserEntity userEntity = UserEntity.builder()
                .email(email)
                .password("테스트비밀번호")
                .name("테스트이름")
                .nickname("테스트별명")
                .profileImage("https://img.url.test")
                .role(Role.USER)
                .build();

        return userRepository.save(userEntity);
    }

    private Crews saveCrew(String name){
        Crews crews = Crews.builder().name(name).crewMembersList(new ArrayList<>()).build();

        return crewRepository.save(crews);
    }

    private CrewMembers generateCrewMember(UserEntity userEntity, Crews crews){
        CrewMembers crewMembers = CrewMembers.builder().role(CrewMemberRole.MASTER).build();

        crews.addCrewMember(crewMembers);
        crewRepository.save(crews);

        crewMembers.registerUser(userEntity);

        return crewMembers;
    }

    @Test
    @DisplayName("크루 맴버 생성")
    void saveCrewMemberTest(){
        //given
        UserEntity userEntity = saveUser("테스트이메일");
        Crews crews = saveCrew("테스트크루");
        CrewMembers crewMembers = generateCrewMember(userEntity, crews);

        //when
        CrewMembers saveCrewMembers = crewMemberRepository.save(crewMembers);

        //then
        assertThat(saveCrewMembers.getCrews().getName()).isEqualTo("테스트크루");
    }

    @Test
    @DisplayName("크루 맴버 아이디로 조회")
    void findCrewMemberTest(){
        //given
        UserEntity userEntity = saveUser("테스트이메일");
        Crews crews = saveCrew("테스트크루");
        CrewMembers crewMembers = generateCrewMember(userEntity, crews);

        //when
        Long saveId = crewMemberRepository.save(crewMembers).getId();

        //then
        CrewMembers findCrewMembers = crewMemberRepository.findById(saveId).get();
        assertThat(findCrewMembers.getCrews().getName()).isEqualTo("테스트크루");
    }

    @Test
    @DisplayName("크루 맴버 아이디로 조회 실패")
    void findCrewMemberFailTest(){
        //given

        //when
        Long saveId = 0L;

        //then
        Optional<CrewMembers> findCrewMember = crewMemberRepository.findById(saveId);
        assertThat(findCrewMember.isPresent()).isFalse();
    }

    @Test
    @DisplayName("크루맴버 삭제")
    void deleteCrewMemberTest(){
        //given
        UserEntity userEntity = saveUser("테스트이메일");
        Crews crews = saveCrew("테스트크루");
        CrewMembers crewMembers = generateCrewMember(userEntity, crews);
        crewMemberRepository.save(crewMembers);

        //when
        crews.removeCrewMember(crewMembers);
        crewRepository.save(crews);

        Long saveId = crewMemberRepository.save(crewMembers).getId();

        //then
        Optional<CrewMembers> deleteCrewMember = crewMemberRepository.findOne(cms.searchWithId(saveId));
        assertThat(deleteCrewMember.isPresent()).isFalse();

    }


}