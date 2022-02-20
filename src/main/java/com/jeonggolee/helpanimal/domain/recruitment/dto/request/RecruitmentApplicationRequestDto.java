package com.jeonggolee.helpanimal.domain.recruitment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentApplicationRequestDto {
    private Long recruitmentId;
    private String email;
    private String comment;
}
