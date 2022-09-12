package com.jeonggolee.helpanimal.domain.recruitment.dto.response;

import com.jeonggolee.helpanimal.domain.recruitment.entity.Recruitment;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentMethod;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecruitmentDetailDto {
    /* 게시글 번호 */
    private Long id;
    /* 공고 명 */
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

    public static RecruitmentDetailDto convertDto(Recruitment recruitment) {
        return RecruitmentDetailDto.builder()
                .id(recruitment.getId())
                .name(recruitment.getName())
                .recruitmentType(recruitment.getRecruitmentType())
                .author(recruitment.getUser().getNickname())
                .content(recruitment.getContent())
                .animalType(recruitment.getAnimal().toString())
                .participant(recruitment.getParticipant())
                .imageUrl(recruitment.getImageUrl())
                .recruitmentMethod(recruitment.getRecruitmentMethod())
                .build();
    }
}
