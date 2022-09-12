package com.jeonggolee.helpanimal.domain.crew.dto;
import com.jeonggolee.helpanimal.domain.crew.domain.Crews;
import com.jeonggolee.helpanimal.domain.crew.enums.CrewMemberRole;
import lombok.Getter;

@Getter
public class ReadCrewDetailDto {
    private Long id;
    private String name;
    private String masterName;
    private int numOfMember;

    public ReadCrewDetailDto(Crews crews){
        this.id = crews.getId();
        this.name = crews.getName();
        this.masterName = crews.getCrewMembersList().stream()
                .filter(member -> member.getRole().equals(CrewMemberRole.MASTER))
                .findAny()
                .orElseThrow(() ->new IllegalStateException("치명적인 오류.(크루 마스터가 없음)"))
                .getUserEntity().getName();

        this.numOfMember = crews.getCrewMembersList().size();
    }
}
