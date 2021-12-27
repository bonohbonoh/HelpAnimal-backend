package com.jeonggolee.helpanimal.common.handler;

import com.jeonggolee.helpanimal.common.execption.UserNotFoundException;
import com.jeonggolee.helpanimal.domain.user.exception.ExceptionStatus;
import com.jeonggolee.helpanimal.domain.user.exception.login.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionStatus> userNotFoundException(UserNotFoundException e) {
        ExceptionStatus response = new ExceptionStatus(e.getMessage(), 400);
        return new ResponseEntity<ExceptionStatus>(response, HttpStatus.BAD_REQUEST);
    }
}
