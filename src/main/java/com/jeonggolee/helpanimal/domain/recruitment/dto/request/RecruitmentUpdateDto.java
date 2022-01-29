package com.jeonggolee.helpanimal.domain.recruitment.dto.request;

import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentMethod;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentUpdateDto {
    /* 공고 명 */
    @NotEmpty(message = "공고 명이 비어있습니다.")
    private String name;
    /* 본문 */
    @NotEmpty(message = "본문이 비어있습니다.")
    private String content;
    /* 총 참가 인원 */
    @NotNull(message = "참가인원이 비어있습니다.")
    private int participant;
    /* 첨부파일 사진 URL */
    @NotEmpty(message = "첨부파일 URl이 비어있습니다.")
    private String imageUrl;
}
