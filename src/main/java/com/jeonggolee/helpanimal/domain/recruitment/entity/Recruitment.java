package com.jeonggolee.helpanimal.domain.recruitment.entity;

import com.jeonggolee.helpanimal.common.entity.BaseTimeEntity;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentMethod;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentType;
import com.jeonggolee.helpanimal.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "recruitments")
public class Recruitment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 공고명 */
    @Column(nullable = false, length = 30)
    private String name;

    /** 공고 구분(일회성, 크루 충원) */
    @Column
    @Enumerated(EnumType.STRING)
    private RecruitmentType recruitmentType;

    /** 등록자 */
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User user;

    /** 내용 */
    @Column(nullable = false)
    private String content;

    /** 봉사 구분 (개, 고양이 등등) */
    @OneToOne
    @JoinColumn(name = "animal_type")
    private Animal animal;

    /** 총 참가 인원 */
    @Column(nullable = false)
    private int participant;

    /** 첨부 이미지 */
    @Column(nullable = true, length = 500)
    private String imageUrl;

    /** 채용 방식 */
    @Column
    @Enumerated(EnumType.STRING)
    private RecruitmentMethod recruitmentMethod;

    /** 공고신청내역 */
    @OneToMany(mappedBy = "recruitment")
    private List<RecruitmentRequest> recruitmentRequests;

    public void updateRecruitmentName(String name) {
        this.name = name;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateParticipant(int participant) {
        this.participant = participant;
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
