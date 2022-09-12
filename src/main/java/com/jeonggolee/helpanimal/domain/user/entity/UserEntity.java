package com.jeonggolee.helpanimal.domain.user.entity;

import com.jeonggolee.helpanimal.common.entity.BaseTimeEntity;
import com.jeonggolee.helpanimal.domain.crew.domain.CrewMembers;
import com.jeonggolee.helpanimal.domain.recruitment.entity.Recruitment;
import com.jeonggolee.helpanimal.domain.recruitment.entity.RecruitmentRequest;
import com.jeonggolee.helpanimal.domain.user.util.Role;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "user")
@Builder
public class UserEntity extends BaseTimeEntity {

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

    @Column(length = 256)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CrewMembers> crewMembers;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Recruitment> recruitments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RecruitmentRequest> recruitmentRequests;

    public void updateRole(Role role) {
        this.role = role;
    }

    public void updateUrl(String url) {
        this.url = url;
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void addRecruitment(Recruitment recruitment) {
        if (this.recruitments == null) {
            this.recruitments = new ArrayList<>();
        }
        recruitment.addUser(this);
        this.recruitments.add(recruitment);
    }

    public void addRecruitmentRequest(RecruitmentRequest request) {
        if (this.recruitmentRequests == null) {
            this.recruitmentRequests = new ArrayList<>();
        }
        request.addUser(this);
        this.recruitmentRequests.add(request);
    }

    public void deleteRecruitment(Recruitment recruitment) {
        if (!this.recruitments.isEmpty() && this.recruitments.contains(recruitment)) {
            this.recruitments.remove(recruitment);
        }
    }

    public void deleteRecruitmentRequest(RecruitmentRequest request) {
        if (!this.recruitmentRequests.isEmpty() && this.recruitmentRequests.contains(request)) {
            this.recruitmentRequests.remove(request);
        }
    }

    public UserEntity(String email, String password, String name, String nickname, String profileImage,
        Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.role = role;
    }
}