package com.jeonggolee.helpanimal.domain.recruitment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentApplicationSearchDto {
    private int numberOfElements;
    private Long totalElements;
    private List<RecruitmentApplicationDetailDto> data;
}
