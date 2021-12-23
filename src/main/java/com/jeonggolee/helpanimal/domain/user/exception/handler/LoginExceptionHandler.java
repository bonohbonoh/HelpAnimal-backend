package com.jeonggolee.helpanimal.domain.user.exception.handler;

import com.jeonggolee.helpanimal.domain.user.exception.ExceptionStatus;
import com.jeonggolee.helpanimal.domain.user.exception.login.EmailPasswordNullPointException;
import com.jeonggolee.helpanimal.domain.user.exception.login.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LoginExceptionHandler {
    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ExceptionStatus> wrongPasswordException(WrongPasswordException wrongPasswordException) {
        ExceptionStatus response = new ExceptionStatus(wrongPasswordException.getMessage(), 403);
        return new ResponseEntity<ExceptionStatus>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EmailPasswordNullPointException.class)
    public ResponseEntity<ExceptionStatus> emailPasswordNullPointException(EmailPasswordNullPointException emailPasswordNullPointException) {
        ExceptionStatus response = new ExceptionStatus(emailPasswordNullPointException.getMessage(), 400);
        return new ResponseEntity<ExceptionStatus>(response, HttpStatus.BAD_REQUEST);
    }
}
