package com.jeonggolee.helpanimal.domain.crew.dto;

import com.jeonggolee.helpanimal.domain.crew.domain.Crew;
import com.jeonggolee.helpanimal.domain.crew.domain.CrewMember;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

@Getter
@AllArgsConstructor
public class UpdateCrewDto {
    @NotEmpty(message = "크루아이디를 입력해주세요.")
    private Long id;
    private String name;
}
