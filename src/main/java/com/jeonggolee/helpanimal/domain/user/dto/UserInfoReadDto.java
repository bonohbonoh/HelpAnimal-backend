package com.jeonggolee.helpanimal.domain.user.dto;

import com.jeonggolee.helpanimal.domain.crew.domain.CrewMembers;
import com.jeonggolee.helpanimal.domain.user.entity.UserEntity;
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
    private String name;

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String profileImage;

    @NotEmpty
    private Role role;

    private List<CrewMembers> crewMembersList;

    public UserInfoReadDto(UserEntity userEntity) {
        this.userId = userEntity.getUserId();
        this.email = userEntity.getEmail();
        this.name = userEntity.getName();
        this.nickname = userEntity.getNickname();
        this.profileImage = userEntity.getProfileImage();
        this.role = userEntity.getRole();
        this.crewMembersList = userEntity.getCrewMembers();
    }
}
