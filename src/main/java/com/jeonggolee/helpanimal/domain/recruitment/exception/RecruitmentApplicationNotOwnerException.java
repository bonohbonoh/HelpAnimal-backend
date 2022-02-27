package com.jeonggolee.helpanimal.domain.recruitment.exception;

public class RecruitmentApplicationNotOwnerException extends RuntimeException {
    public RecruitmentApplicationNotOwnerException(String msg) {
        super(msg);
    }
}
