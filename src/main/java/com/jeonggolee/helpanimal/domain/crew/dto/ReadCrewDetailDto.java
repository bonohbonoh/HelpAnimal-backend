package com.jeonggolee.helpanimal.domain.crew.dto;
import com.jeonggolee.helpanimal.domain.crew.domain.Crews;
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
    public static ReadCrewDetailDto from(Crews crews){
        return ReadCrewDetailDto.builder()
                .id(crews.getId())
                .name(crews.getName())
                .masterName(crews.getCrewMembersList().stream()
                        .filter(member -> member.getRole().equals(CrewMemberRole.MASTER))
                        .findAny()
                        .orElseThrow(() ->new IllegalStateException("치명적인 오류.(크루 마스터가 없음)"))
                        .getUser().getName())
                .numOfMember(crews.getCrewMembersList().size())
                .build();
    }
}
