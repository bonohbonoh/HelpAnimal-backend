package com.jeonggolee.helpanimal.domain.crew.dto;

import com.jeonggolee.helpanimal.domain.crew.domain.Crew;
import com.jeonggolee.helpanimal.domain.crew.domain.CrewMember;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class CreateCrewDto {
    @NotEmpty(message = "크루명을 입력해주세요.")
    private String name;

    public Crew toEntity(CrewMember crewMaster){
        return Crew.builder()
                .name(this.name)
                .crewMemberList(new ArrayList<>())
                .build();
    }
}
