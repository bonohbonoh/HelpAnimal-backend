package com.jeonggolee.helpanimal.domain.user.exception.auth;

public class WrongAuthenticationUrlException extends RuntimeException {
    public WrongAuthenticationUrlException(String msg) {
        super(msg);
    }
}