package com.jeonggolee.helpanimal.domain.user.entity;

import com.jeonggolee.helpanimal.common.eneity.BaseTimeEntity;
import com.jeonggolee.helpanimal.domain.crew.domain.CrewMember;
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
@Builder
public class User extends BaseTimeEntity {

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
    private String nickname;

    @Column(nullable = false) // 프로필 사진 필요시
    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<CrewMember> crewMemberList;

    public String getRoleKey() {
        return this.role.getKey();
    }

    public void updateProfileImage(String profileImage){
        this.profileImage = profileImage;
    }

    public void updateNickname(String nickname){
        this.nickname = nickname;
    }

    public void updatePassword(String password){
        this.password = password;
    }

}