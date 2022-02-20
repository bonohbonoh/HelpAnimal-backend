package com.jeonggolee.helpanimal.domain.recruitment.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecruitmentApplicationStatus {

    ACCEPTANCE("ACCEPTANCE","수락"),
    REQUEST("REQUEST","요청"),
    REJECT("REJECT","거절");

    private final String value;
    private final String description;


}
