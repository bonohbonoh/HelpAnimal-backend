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
class CrewMemberTest {
    private CrewMember crewMember;
    private User user;
    private Crew crew;

    @BeforeAll
    void beforeAll(){
        user = User.builder()
                .email("테스트이메일")
                .password("테스트비밀번호")
                .name("테스트이름")
                .nickname("테스트별명")
                .profileImage("https://img.url.test")
                .role(Role.USER)
                .build();

        crew = Crew.builder()
                .id(0L)
                .name("테스트크루")
                .crewMemberList(new ArrayList<>())
                .build();

        crewMember = CrewMember.builder()
                .id(0L)
                .user(null)
                .crew(null)
                .role(CrewMemberRole.MASTER)
                .build();


    }

    @Test
    void updateCrewMemberRoleTest() {
        crewMember.updateCrewMemberRole(CrewMemberRole.MEMBER);

        assertThat(crewMember.getRole()).isEqualTo(CrewMemberRole.MEMBER);
    }

    @Test
    void registerCrewTest() {
        crewMember.registerCrew(crew);

        assertThat(crewMember.getCrew()).isEqualTo(crew);
    }

    @Test
    void registerUserTest() {
        crewMember.registerUser(user);

        assertThat(crewMember.getUser()).isEqualTo(user);
    }

    @Test
    void deleteTest() {
        crewMember.delete();

        assertThat(crewMember.getDeletedAt()).isNotNull();
    }
}