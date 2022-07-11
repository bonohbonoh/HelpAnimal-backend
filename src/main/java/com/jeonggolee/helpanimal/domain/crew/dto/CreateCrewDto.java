package com.jeonggolee.helpanimal.domain.crew.dto;

import com.jeonggolee.helpanimal.domain.crew.domain.Crews;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCrewDto {
    @NotEmpty(message = "크루명을 입력해주세요.")
    private String name;

    public Crews toEntity(){
        return Crews.builder()
                .name(this.name)
                .crewMembersList(new ArrayList<>())
                .build();
    }
}
