package com.jeonggolee.helpanimal.domain.recruitment.entity;

import com.jeonggolee.helpanimal.common.eneity.BaseTimeEntity;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentApplicationStatus;
import com.jeonggolee.helpanimal.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class RecruitmentApplication extends BaseTimeEntity {
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
}
