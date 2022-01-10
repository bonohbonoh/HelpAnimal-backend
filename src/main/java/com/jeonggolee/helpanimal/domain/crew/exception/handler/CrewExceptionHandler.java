package com.jeonggolee.helpanimal.domain.crew.exception.handler;

import com.jeonggolee.helpanimal.common.exception.CrewNameDuplicateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = {"com.jeonggolee.helpanimal.domain.crew"})
public class CrewExceptionHandler {

    @ExceptionHandler(CrewNameDuplicateException.class)
    public ResponseEntity<String> handleCrewNameDuplicateException(CrewNameDuplicateException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
