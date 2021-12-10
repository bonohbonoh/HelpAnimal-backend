package com.jeonggolee.helpanimal.domain.user.entity;

import com.jeonggolee.helpanimal.domain.user.util.Role;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 40, unique = true)
    private String email;

    @Column(nullable = false, length = 256)
    private String password;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 30, unique = true)
    private String nickName;

    @Column(nullable = false) // 프로필 사진 필요시
    private String profileImage;

    /*
    모집공고 조인 필요시
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "recruitment")
    private List<Recruitment> recruitmentList;
     */

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String email, String password, String name, String nickName, String profileImage, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.profileImage = profileImage;
        this.role = role;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}

