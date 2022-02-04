package com.jeonggolee.helpanimal.domain.user.exception.signup;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "User Not Found")
public class UserInfoNotFoundException extends RuntimeException {
    public UserInfoNotFoundException(String msg) {
        super(msg);
    }
}
