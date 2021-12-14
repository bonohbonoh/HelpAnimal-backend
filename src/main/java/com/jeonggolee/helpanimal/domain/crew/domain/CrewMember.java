package com.jeonggolee.helpanimal.domain.crew.domain;

import com.jeonggolee.helpanimal.common.eneity.BaseTimeEntity;
import com.jeonggolee.helpanimal.domain.crew.enums.CrewMemberRole;
import com.jeonggolee.helpanimal.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Getter
@SequenceGenerator(
        name = "crew_member_idx_generator",
        sequenceName = "crew_member_idx"
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "crew_member")
public class CrewMember extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "crew_member_idx_generator")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "crew_id")
    private Crew crew;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CrewMemberRole role;

    public void updateCrewMemberRole(CrewMemberRole role){
        this.role = role;
    }

    public void registerCrew(Crew crew){
        this.crew = crew;
    }

    public void registerUser(User user){
        this.user = user;
    }

    private void removeCrew(){
        this.crew = null;
    }

    private void removeUser(){
        this.user = null;
    }

    @Override
    public void delete(){
        super.delete();
        this.removeCrew();
        this.removeUser();
    }
}
