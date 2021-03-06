package com.jeonggolee.helpanimal.domain.crew.domain;

import com.jeonggolee.helpanimal.common.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@SequenceGenerator(
        name = "crew_idx_generator",
        sequenceName = "crew_idx"
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "crews")
public class Crews extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "crew_idx_generator")
    private Long id;

    @Column(length = 20, nullable = false, unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "crew_id")
    private List<CrewMembers> crewMembersList;

    //TODO: 공고 테이블이 추가되면 OneToMany 추가

    //TODO: 크루 스케쥴 테이블이 추가되면 OneToMany 추가(단방향 예정)

    //TODO: 크루 게시판 테이블이 추가되면 OneToMany 추가(단방향 예정)

    public void updateName(String name){
        this.name = name;
    }

    public void addCrewMember(CrewMembers newCrewMembers){
        crewMembersList.add(newCrewMembers);
        newCrewMembers.registerCrew(this);
    }

    public void removeCrewMember(CrewMembers deleteCrewMembers){
        crewMembersList.remove(deleteCrewMembers);
        deleteCrewMembers.delete();
    }

    @Override
    public void delete(){
        super.delete();
        this.crewMembersList.forEach(CrewMembers::delete);
    }

}
