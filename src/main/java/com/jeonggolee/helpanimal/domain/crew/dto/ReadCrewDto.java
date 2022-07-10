package com.jeonggolee.helpanimal.domain.crew.dto;
import com.jeonggolee.helpanimal.domain.crew.domain.Crew;
import com.jeonggolee.helpanimal.domain.crew.enums.CrewMemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadCrewDto {
    private Long id;
    private String name;
    private String masterName;
    private String introduction;
    private String profileImage;
    private Long members;
    public static ReadCrewDto from(Crew crew){
        return ReadCrewDto.builder()
                .id(crew.getId())
                .name(crew.getName())
                .masterName(crew.getCrewMemberList().stream()
                        .filter(member -> member.getRole().equals(CrewMemberRole.MASTER))
                        .findAny()
                        .orElseThrow(() ->new IllegalStateException("치명적인 오류.(크루 마스터가 없음)"))
                        .getUser().getName())
                .introduction(crew.getIntroduction())
                .build();
    }
}
