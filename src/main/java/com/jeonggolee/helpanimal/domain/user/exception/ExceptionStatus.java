package com.jeonggolee.helpanimal.domain.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionStatus {

    private String msg;

    private HttpStatus status;

}
