package com.jeonggolee.helpanimal.domain.crew.dto;

import com.jeonggolee.helpanimal.domain.crew.domain.Crew;
import com.jeonggolee.helpanimal.domain.crew.domain.CrewMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCrewDto {
    @NotEmpty(message = "크루아이디를 입력해주세요.")
    private Long id;//<<수정시 필요한 아이디 일부러 여기에 넣어봄 경로변수 안쓰고,,,
    private String name;
}
