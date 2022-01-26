package com.jeonggolee.helpanimal.domain.user.exception.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EmailPasswordNullPointException extends RuntimeException {
    public EmailPasswordNullPointException(String msg) {
        super(msg);
    }
}
