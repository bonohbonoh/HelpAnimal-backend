package com.jeonggolee.helpanimal.domain.crew.dto;
import com.jeonggolee.helpanimal.domain.crew.domain.Crew;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ReadCrewDto {
    private Long id;
    private String name;
    private String masterName;
    private String introduction;
    private String profileImage;
    private Long members;

    public ReadCrewDto(Crew crew){
        this.id = crew.getId();
        this.name = crew.getName();
        this.masterName = crew.getCrewMemberList().stream()
                .filter(member -> member.getRole().toString().equals("마스터"))
                .toString();
        this.introduction = crew.getIntroduction();

        //@정지영 파일서비스 구현 및 querydsl 적용이 필요해 보임
        this.profileImage = null;
        this.members = (long) crew.getCrewMemberList().size();

    }


}
