package com.jeonggolee.helpanimal.domain.crew.dto;
import com.jeonggolee.helpanimal.domain.crew.domain.Crews;
import lombok.Getter;

@Getter
public class ReadCrewDto {
    private Long id;
    private String name;
    private String masterName;

    public ReadCrewDto(Crews crews){
        this.id = crews.getId();
        this.name = crews.getName();
        this.masterName = crews.getCrewMembersList().stream()
                .filter(member -> member.getRole().toString().equals("마스터"))
                .toString();
    }


}
