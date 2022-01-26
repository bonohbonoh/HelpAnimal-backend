package com.jeonggolee.helpanimal.domain.user.exception.handler;

import com.jeonggolee.helpanimal.domain.user.exception.ExceptionStatus;
import com.jeonggolee.helpanimal.domain.user.exception.auth.WrongAuthenticationUrlException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthenticationHandler {
    @ExceptionHandler(WrongAuthenticationUrlException.class)
    public ResponseEntity<ExceptionStatus> wrongAuthenticationUrlException(WrongAuthenticationUrlException wrongAuthenticationUrl) {
        ExceptionStatus response = new ExceptionStatus(wrongAuthenticationUrl.getMessage(), 400);
        return new ResponseEntity<ExceptionStatus>(response, HttpStatus.BAD_REQUEST);
    }
}
