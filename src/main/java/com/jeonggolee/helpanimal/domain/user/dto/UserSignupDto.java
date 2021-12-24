package com.jeonggolee.helpanimal.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeonggolee.helpanimal.domain.user.entity.User;
import com.jeonggolee.helpanimal.domain.user.util.Enum;
import com.jeonggolee.helpanimal.domain.user.util.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignupDto {

    @NotEmpty(message = "이메일을 입력해주세요")
    @Size(min = 2,max = 40,message = "이메일 길이는 2글자에서 40글자 사이입니다.")
    @Email
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    @Size(min = 2,max = 16,message = "비밀번호는 2글자에서 16글자 사이입니다.")
    private String password;

    @NotEmpty(message = "이름을 입력해주세요")
    @Size(min = 2,max = 10,message = "이름은 2글자에서 10글자 사이입니다.")
    private String name;

    @NotEmpty(message = "닉네임을 입력해주세요")
    @Size(min = 2,max = 30,message = "닉네임은 2글자에서 30글자 사이입니다.")
    private String nickname;

    @NotEmpty(message = "프로필 설정을 해주세요.")
    private String profileImag;

    @NotEmpty(message = "권한이 없습니다.")
    @Enum(enumClass = Role.class, ignoreCase = true)
    private String role;

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
