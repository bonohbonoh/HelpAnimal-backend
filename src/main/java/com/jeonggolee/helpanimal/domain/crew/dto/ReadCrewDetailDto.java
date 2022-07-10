package com.jeonggolee.helpanimal.domain.crew.dto;
import com.jeonggolee.helpanimal.domain.crew.domain.Crew;
import com.jeonggolee.helpanimal.domain.crew.enums.CrewMemberRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadCrewDetailDto {
    private Long id;
    private String name;
    private String masterName;
    private int numOfMember;
    public static ReadCrewDetailDto from(Crew crew){
        return ReadCrewDetailDto.builder()
                .id(crew.getId())
                .name(crew.getName())
                .masterName(crew.getCrewMemberList().stream()
                        .filter(member -> member.getRole().equals(CrewMemberRole.MASTER))
                        .findAny()
                        .orElseThrow(() ->new IllegalStateException("치명적인 오류.(크루 마스터가 없음)"))
                        .getUser().getName())
                .numOfMember(crew.getCrewMemberList().size())
                .build();
    }
}
