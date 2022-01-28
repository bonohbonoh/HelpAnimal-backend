package com.jeonggolee.helpanimal.domain.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionStatus {
    private String msg;
    private Integer status;

}
