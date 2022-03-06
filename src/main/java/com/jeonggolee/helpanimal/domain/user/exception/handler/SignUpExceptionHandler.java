package com.jeonggolee.helpanimal.domain.user.exception.handler;

import com.jeonggolee.helpanimal.domain.user.exception.signup.UserDuplicationException;
import com.jeonggolee.helpanimal.domain.user.exception.signup.UserInfoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class SignUpExceptionHandler {
    @ExceptionHandler(UserDuplicationException.class)
    public ResponseEntity<String> duplicationException(UserDuplicationException duplicationException) {
        return new ResponseEntity<>(duplicationException.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserInfoNotFoundException.class)
    public ResponseEntity<String> userInfoNotFoundException(UserInfoNotFoundException userInfoNotFoundException) {
        return new ResponseEntity<>(userInfoNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
