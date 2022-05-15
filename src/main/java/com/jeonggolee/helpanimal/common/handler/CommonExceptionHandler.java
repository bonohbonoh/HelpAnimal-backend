package com.jeonggolee.helpanimal.common.handler;

import com.jeonggolee.helpanimal.common.exception.AnimalNotFoundException;
import com.jeonggolee.helpanimal.common.exception.RecruitmentApplicationNotFoundException;
import com.jeonggolee.helpanimal.common.exception.RecruitmentNotFoundException;
import com.jeonggolee.helpanimal.common.exception.UserNotFoundException;
import com.jeonggolee.helpanimal.common.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ResponseDto.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidExcepton(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
            .getAllErrors()
            .get(0)
            .getDefaultMessage();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ResponseDto.fail(HttpStatus.BAD_REQUEST.value(), message));
    }

    @ExceptionHandler(AnimalNotFoundException.class)
    public ResponseEntity<?> animalNotFoundException(AnimalNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ResponseDto.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler(RecruitmentNotFoundException.class)
    public ResponseEntity<?> recruitmentNotFoundException(RecruitmentNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ResponseDto.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler(RecruitmentApplicationNotFoundException.class)
    public ResponseEntity<?> recruitmentApplicationNotFoundException(
        RecruitmentApplicationNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ResponseDto.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> illegalStateException(
        IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ResponseDto.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }
}

