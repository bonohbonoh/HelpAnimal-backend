package com.jeonggolee.helpanimal.domain.recruitment.exception;

public class RecruitmentNotOwnerException extends RuntimeException {
    public RecruitmentNotOwnerException(String msg) {
        super(msg);
    }
}
