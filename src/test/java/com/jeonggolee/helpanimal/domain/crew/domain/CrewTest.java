package com.jeonggolee.helpanimal.domain.crew.domain;

import com.jeonggolee.helpanimal.domain.crew.enums.CrewMemberRole;
import com.jeonggolee.helpanimal.domain.user.entity.User;
import com.jeonggolee.helpanimal.domain.user.util.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CrewTest {
    private Crew crew;
    private CrewMember crewMember;

    @BeforeAll
    void beforeAll(){
        User user = User.builder()
                .email("테스트이메일")
                .password("테스트비밀번호")
                .name("테스트이름")
                .nickname("테스트별명")
                .profileImage("https://img.url.test")
                .role(Role.USER)
                .build();


        crewMember = CrewMember.builder()
                .id(0L)
                .user(user)
//                .crew(crew)
                .role(CrewMemberRole.MASTER)
                .build();

        crew = Crew.builder()
                .id(0L)
                .name("테스트크루")
                .crewMemberList(new ArrayList<>())
                .build();

    }

    @Test
    void updateNameTest(){
        crew.updateName("변경된테스트이름");

        assertThat(crew.getName()).isEqualTo("변경된테스트이름");
    }

    @Test
    void addCrewMemberTest(){
        crew.addCrewMember(crewMember);

        assertThat(crew.getCrewMemberList().get(0).getId()).isEqualTo(0L);
    }

    @Test
    void removeCrewMemberTest(){
        crew.removeCrewMember(crewMember);

        assertThat(crew.getCrewMemberList().size()).isEqualTo(0);
    }

    @Test
    void deleteTest(){
        crew.delete();

        assertThat(crew.getDeletedAt()).isNotNull();

    }


}