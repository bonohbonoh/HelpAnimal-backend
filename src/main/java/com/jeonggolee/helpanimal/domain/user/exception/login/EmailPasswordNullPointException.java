package com.jeonggolee.helpanimal.domain.user.exception.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Login Not Found")
public class EmailPasswordNullPointException extends RuntimeException{
    public EmailPasswordNullPointException(String msg) {
        super(msg);
    }
}
