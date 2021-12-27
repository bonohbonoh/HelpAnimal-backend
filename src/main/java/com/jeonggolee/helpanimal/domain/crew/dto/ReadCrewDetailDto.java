package com.jeonggolee.helpanimal.domain.crew.dto;
import com.jeonggolee.helpanimal.domain.crew.domain.Crew;
import lombok.Getter;

@Getter
public class ReadCrewDetailDto {
    private Long id;
    private String name;
    private String masterName;
    private int numOfMember;

    public ReadCrewDetailDto(Crew crew){
        this.id = crew.getId();
        this.name = crew.getName();
        this.masterName = crew.getCrewMemberList().stream()
                .filter(member -> member.getRole().toString().equals("마스터"))
                .toString();
        this.numOfMember = crew.getCrewMemberList().size();
    }
}
