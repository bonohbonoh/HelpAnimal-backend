package com.jeonggolee.helpanimal.domain.recruitment.exception.handler;

import com.jeonggolee.helpanimal.domain.recruitment.exception.RecruitmentNotOwnerException;
import com.jeonggolee.helpanimal.domain.user.exception.ExceptionStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = {"com.jeonggolee.helpanimal.domain.recruitment"})
public class RecruitmentExceptionHandler {
    @ExceptionHandler(RecruitmentNotOwnerException.class)
    public ResponseEntity<ExceptionStatus> recruitmentNotOwnerException(RecruitmentNotOwnerException e) {
        ExceptionStatus response = new ExceptionStatus(e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<ExceptionStatus>(response, HttpStatus.BAD_REQUEST);
    }
}
