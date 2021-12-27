package com.jeonggolee.helpanimal.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserLoginDto {

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email
    @Size(min = 2, max = 40, message = "이메일 길이는 2글자에서 40글자 사이 입니다.")
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    @Size(min = 2, max = 16, message = "비밀번호는 2글자에서 16글자 사이입니다.")
    private String password;
}
