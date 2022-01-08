package com.jeonggolee.helpanimal.domain.user.dto;

import com.jeonggolee.helpanimal.domain.crew.domain.CrewMember;
import com.jeonggolee.helpanimal.domain.user.entity.User;
import com.jeonggolee.helpanimal.domain.user.util.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserInfoReadDto {
    @NotEmpty
    private Long userId;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String name;

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String profileImage;

    @NotEmpty
    private Role role;

    private List<CrewMember> crewMemberList;

    public UserInfoReadDto(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
        this.role = user.getRole();
        this.crewMemberList = user.getCrewMemberList();
    }
}