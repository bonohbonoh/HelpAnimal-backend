package com.jeonggolee.helpanimal.domain.crew.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@SequenceGenerator(
        name = "crew_idx_generator",
        sequenceName = "crew_idx"
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "crew")
public class Crew {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "crew_idx_generator")
    private Long id;

    @Column(length = 20, nullable = false, unique = true)
    private String name;

    //TODO: 사용자 테이블과 조인방식 및 권한처리 방식 고민..
    //테이블 어떻게 구성할지 조언 부탁드립니다.
    //맴버에 다 때려박고, 그 안에서 등급을 구분할지(이러면 크루별 권한 컬럼을 어떻게 할지가 고민이고)
    //아니면 마스터 하나 두고 크루 맴버로 따로 관리할지(이러면 중간급 마스터는 어떻게 해야할지 고민이고) <- 객체지향적이지 않을것 같음

    /*
    @OneToOne(mappedBy = "crew")
    private User crewMaster;
    */

    /*
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "crew_id")
    private List<User> crewMember;
    */

    //TODO: 공고 테이블이 추가되면 OneToMany 추가

    //TODO: 크루 스케쥴 테이블이 추가되면 OneToMany 추가(단방향 예정)

    //TODO: 크루 게시판 테이블이 추가되면 OneToMany 추가(단방향 예정)



}
