package com.jeonggolee.helpanimal.domain.user.exception.handler;

import com.jeonggolee.helpanimal.domain.user.exception.login.EmailPasswordNullPointException;
import com.jeonggolee.helpanimal.domain.user.exception.login.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LoginExceptionHandler {
    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<String> wrongPasswordException(WrongPasswordException wrongPasswordException) {
        return new ResponseEntity<>(wrongPasswordException.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EmailPasswordNullPointException.class)
    public ResponseEntity<String> emailPasswordNullPointException(EmailPasswordNullPointException emailPasswordNullPointException) {
        return new ResponseEntity<>(emailPasswordNullPointException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
