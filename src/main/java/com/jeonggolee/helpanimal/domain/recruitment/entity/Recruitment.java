package com.jeonggolee.helpanimal.domain.recruitment.entity;

import com.jeonggolee.helpanimal.common.entity.BaseTimeEntity;
import com.jeonggolee.helpanimal.domain.recruitment.enums.Animal;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentMethod;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentType;
import com.jeonggolee.helpanimal.domain.user.entity.UserEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

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

    /**
     * 공고명
     */
    @Column(nullable = false, length = 30)
    private String name;

    /**
     * 공고 구분(일회성, 크루 충원)
     */
    @Column
    @Enumerated(EnumType.STRING)
    private RecruitmentType recruitmentType;

    /**
     * 등록자
     */
    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity user;

    /**
     * 내용
     */
    @Column(nullable = false)
    private String content;

    /**
     * 봉사 구분 (개, 고양이 등등)
     */
    @Column
    @Enumerated(value = EnumType.STRING)
    private Animal animal;

    /**
     * 총 참가 인원
     */
    @Column(nullable = false)
    private int participant;

    /**
     * 첨부 이미지
     */
    @Column(nullable = true, length = 500)
    private String imageUrl;

    /**
     * 채용 방식
     */
    @Column
    @Enumerated(EnumType.STRING)
    private RecruitmentMethod recruitmentMethod;

    /**
     * 공고신청내역
     */
    @BatchSize(size = 1000)
    @OneToMany(mappedBy = "recruitment", cascade = CascadeType.ALL)
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

    public void addUser(UserEntity userEntity) {
        this.user = userEntity;
    }


    public void addRequest(RecruitmentRequest request) {
        if (this.recruitmentRequests == null) {
            this.recruitmentRequests = new ArrayList<>();
        }
        request.addRecruitment(this);
        this.recruitmentRequests.add(request);
    }

    public void deleteRequest(RecruitmentRequest request) {
        if (!this.recruitmentRequests.isEmpty() && this.recruitmentRequests.contains(request)) {
            this.recruitmentRequests.remove(request);
        }
    }
}
