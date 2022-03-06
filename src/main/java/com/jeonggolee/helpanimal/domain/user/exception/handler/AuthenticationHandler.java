package com.jeonggolee.helpanimal.domain.user.exception.handler;

import com.jeonggolee.helpanimal.domain.user.exception.auth.WrongAuthenticationUrlException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthenticationHandler {
    @ExceptionHandler(WrongAuthenticationUrlException.class)
    public ResponseEntity<String> wrongAuthenticationUrlException(WrongAuthenticationUrlException wrongAuthenticationUrl) {
        return new ResponseEntity<>(wrongAuthenticationUrl.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
