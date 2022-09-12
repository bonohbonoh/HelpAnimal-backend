package com.jeonggolee.helpanimal.domain.recruitment.dto.request;

import com.jeonggolee.helpanimal.domain.recruitment.entity.Animal;
import com.jeonggolee.helpanimal.domain.recruitment.entity.Recruitment;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentMethod;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentRegistDto {

    /**
     * 공고 명
     */
    @NotEmpty(message = "공고명을 입력해주세요")
    private String name;
    /**
     * 작성자 email
     */
    @NotEmpty(message = "작성자 Email이 없습니다.")
    private String email;
    /**
     * 본문
     */
    @NotEmpty(message = "본문을 입력해주세요")
    private String content;
    /**
     * 동물
     */
    @NotEmpty(message = "봉사구분(동물)이 선택되지 않았습니다.")
    private String animal;
    /**
     * 참가자 수
     */
    @NotNull(message = "참가자 수를 입력해주세요")
    private int participant;
    /**
     * 공고 구분
     */
    @NotNull(message = "공고 구분을 선택해주세요")
    private RecruitmentType recruitmentType;
    /**
     * 채용방법
     */
    @NotNull(message = "채용방법을 선택해주세요")
    private RecruitmentMethod recruitmentMethod;

    public Recruitment toEntity() {
        return Recruitment.builder()
            .name(this.name)
            .content(this.content)
            .animal(Animal.valueOf(this.animal))
            .recruitmentType(this.recruitmentType)
            .recruitmentMethod(this.recruitmentMethod)
            .participant(this.participant)
            .build();
    }

}
