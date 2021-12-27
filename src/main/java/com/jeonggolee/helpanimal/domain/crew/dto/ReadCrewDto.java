package com.jeonggolee.helpanimal.domain.crew.dto;
import com.jeonggolee.helpanimal.domain.crew.domain.Crew;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ReadCrewDto {
    private Long id;
    private String name;
    private String masterName;

    public ReadCrewDto(Crew crew){
        this.id = crew.getId();
        this.name = crew.getName();
        this.masterName = crew.getCrewMemberList().stream()
                .filter(member -> member.getRole().toString().equals("마스터"))
                .toString();
    }


}
