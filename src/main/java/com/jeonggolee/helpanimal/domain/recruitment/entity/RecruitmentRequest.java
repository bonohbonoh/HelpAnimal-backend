package com.jeonggolee.helpanimal.domain.recruitment.entity;

import com.jeonggolee.helpanimal.common.entity.BaseTimeEntity;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentApplicationStatus;
import com.jeonggolee.helpanimal.domain.user.entity.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "recruitment_requests")
public class RecruitmentRequest extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 50, nullable = false)
    private String comment;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RecruitmentApplicationStatus status;

    public void updateComment(String comment) {
        this.comment = comment;
    }

    public void addRecruitment(Recruitment recruitment) {
        this.recruitment = recruitment;
    }

    public void addUser(User user) {
        this.user = user;
    }
}
