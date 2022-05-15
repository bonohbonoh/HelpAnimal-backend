package com.jeonggolee.helpanimal.domain.recruitment.entity;

import com.jeonggolee.helpanimal.common.entity.BaseTimeEntity;
import com.jeonggolee.helpanimal.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Animal extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 이름 ( 개, 고양이, 사자, 호랑이, 낙타, 타조 등등)
     */

    @Column(nullable = false, unique = true, length = 10)
    private String name;

    /**
     * 등록자
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void updateAnimalName(String name) {
        this.name = name;
    }
}
