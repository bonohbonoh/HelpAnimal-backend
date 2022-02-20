package com.jeonggolee.helpanimal.domain.recruitment.dto.response;

import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentApplicationDetailDto {
    private Long id;
    private Long recruitmentId;
    private String recruitmentName;
    private String email;
    private String comment;
    private RecruitmentApplicationStatus status;
}
