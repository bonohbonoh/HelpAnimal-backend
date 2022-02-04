package com.jeonggolee.helpanimal.domain.user.exception.handler;

import com.jeonggolee.helpanimal.domain.user.exception.ExceptionStatus;
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
    public ResponseEntity<ExceptionStatus> duplicationException(UserDuplicationException duplicationException) {
        ExceptionStatus response = new ExceptionStatus(duplicationException.getMessage(), HttpStatus.CONFLICT);
        return new ResponseEntity<ExceptionStatus>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserInfoNotFoundException.class)
    public ResponseEntity<ExceptionStatus> userInfoNotFoundException(UserInfoNotFoundException userInfoNotFoundException) {
        ExceptionStatus response = new ExceptionStatus(userInfoNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<ExceptionStatus>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionStatus> methodValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        ExceptionStatus response = new ExceptionStatus(e.getBindingResult().getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<ExceptionStatus>(response, HttpStatus.BAD_REQUEST);
    }
}
