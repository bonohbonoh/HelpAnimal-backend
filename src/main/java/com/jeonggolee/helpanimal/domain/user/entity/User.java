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

    @Column(nullable = false, length = 40)
    private String email;

    @Column(nullable = false, length = 32)
    private String passWord;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 30)
    private String nickName;

    @Column(nullable = false) // 프로필 사진 필요시
    private String picture;

    /*
    모집공고 조인 필요시
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "recruitment")
    private List<Recruitment> recruitmentList;
    @Enumerated(EnumType.STRING)
     */

    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String email, String passWord, String name, String nickName, String picture, Role role) {
        this.email = email;
        this.passWord = passWord;
        this.name = name;
        this.nickName = nickName;
        this.picture = picture;
        this.role = role;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}

