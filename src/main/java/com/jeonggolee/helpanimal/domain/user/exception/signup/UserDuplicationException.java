package com.jeonggolee.helpanimal.domain.user.exception.signup;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Duplicate User")
public class UserDuplicationException extends RuntimeException {
    public UserDuplicationException(String msg) {
        super(msg);
    }
}
