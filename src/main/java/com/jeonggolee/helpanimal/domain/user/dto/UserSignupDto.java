package com.jeonggolee.helpanimal.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeonggolee.helpanimal.domain.user.entity.User;
import com.jeonggolee.helpanimal.domain.user.util.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignupDto {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String name;

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String profileImag;

    @NotEmpty
    private Role role;

    @JsonIgnore
    public void PasswordEncoding(String password) {
        this.password = password;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .profileImage(profileImag)
                .role(Role.GUEST)
                .build();
    }
}
