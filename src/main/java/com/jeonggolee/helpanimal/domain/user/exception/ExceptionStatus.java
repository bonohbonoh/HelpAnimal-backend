package com.jeonggolee.helpanimal.domain.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionStatus {
    private String msg;
    private int status;

}
