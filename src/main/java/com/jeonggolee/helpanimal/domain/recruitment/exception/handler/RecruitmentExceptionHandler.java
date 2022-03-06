package com.jeonggolee.helpanimal.domain.recruitment.exception.handler;

import com.jeonggolee.helpanimal.domain.recruitment.exception.RecruitmentNotOwnerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = {"com.jeonggolee.helpanimal.domain.recruitment"})
public class RecruitmentExceptionHandler {
    @ExceptionHandler(RecruitmentNotOwnerException.class)
    public ResponseEntity<String> recruitmentNotOwnerException(RecruitmentNotOwnerException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
