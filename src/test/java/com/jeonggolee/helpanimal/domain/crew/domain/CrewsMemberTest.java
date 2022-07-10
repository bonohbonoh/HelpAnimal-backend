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
class CrewsMemberTest {
    private CrewMembers crewMembers;
    private User user;
    private Crews crews;

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

        crews = Crews.builder()
                .id(0L)
                .name("테스트크루")
                .crewMembersList(new ArrayList<>())
                .build();

        crewMembers = CrewMembers.builder()
                .id(0L)
                .user(null)
                .crews(null)
                .role(CrewMemberRole.MASTER)
                .build();


    }

    @Test
    void updateCrewMemberRoleTest() {
        crewMembers.updateCrewMemberRole(CrewMemberRole.MEMBER);

        assertThat(crewMembers.getRole()).isEqualTo(CrewMemberRole.MEMBER);
    }

    @Test
    void registerCrewTest() {
        crewMembers.registerCrew(crews);

        assertThat(crewMembers.getCrews()).isEqualTo(crews);
    }

    @Test
    void registerUserTest() {
        crewMembers.registerUser(user);

        assertThat(crewMembers.getUser()).isEqualTo(user);
    }

    @Test
    void deleteTest() {
        crewMembers.delete();

        assertThat(crewMembers.getDeletedAt()).isNotNull();
    }
}