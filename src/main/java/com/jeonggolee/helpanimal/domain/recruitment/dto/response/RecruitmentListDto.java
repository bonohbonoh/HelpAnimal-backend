package com.jeonggolee.helpanimal.domain.recruitment.dto.response;

import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentMethod;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentListDto {
    /* 게시글 번호 */
    private Long id;
    /* 공고명 */
    private String name;
    /* 공고 구분 */
    private RecruitmentType recruitmentType;
    /* 등록자 */
    private String author;
    /* 본문 */
    private String content;
    /* 봉사 구분 */
    private String animalType;
    /* 총 참가 인원 */
    private int participant;
    /* 첨부파일 사진 URL */
    private String imageUrl;
    /* 채용 방식 */
    private RecruitmentMethod recruitmentMethod;


}
