package com.jeonggolee.helpanimal.domain.crew.dto;
import com.jeonggolee.helpanimal.domain.crew.domain.Crews;
import com.jeonggolee.helpanimal.domain.crew.enums.CrewMemberRole;
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
    public static ReadCrewDto from(Crews crews){
        return ReadCrewDto.builder()
                .id(crews.getId())
                .name(crews.getName())
                .masterName(crews.getCrewMembersList().stream()
                        .filter(member -> member.getRole().equals(CrewMemberRole.MASTER))
                        .findAny()
                        .orElseThrow(() ->new IllegalStateException("치명적인 오류.(크루 마스터가 없음)"))
                        .getUser().getName())
                .introduction(crews.getIntroduction())
                .build();
    }
}
