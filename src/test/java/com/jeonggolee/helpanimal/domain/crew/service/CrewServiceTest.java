package com.jeonggolee.helpanimal.domain.crew.service;

import com.jeonggolee.helpanimal.domain.crew.dto.CreateCrewDto;
import com.jeonggolee.helpanimal.domain.crew.dto.ReadCrewDetailDto;
import com.jeonggolee.helpanimal.domain.crew.dto.ReadCrewDto;
import com.jeonggolee.helpanimal.domain.crew.dto.UpdateCrewDto;
import com.jeonggolee.helpanimal.domain.user.entity.User;
import com.jeonggolee.helpanimal.domain.user.repository.UserRepository;
import com.jeonggolee.helpanimal.domain.user.util.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class CrewServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CrewService crewService;

    private String saveUser(){
        User user = User.builder()
                .email("userTestEmail@test.com")
                .password("테스트비밀번호")
                .name("테스트이름")
                .nickname("테스트별명")
                .profileImage("https://img.url.test")
                .role(Role.USER)
                .build();

        return userRepository.save(user).getEmail();
    }

    @Test
    @DisplayName("크루 생성")
    void createCrew() {
        //given
        String userEmail = saveUser();
        CreateCrewDto createCrewDto = new CreateCrewDto("테스트크루");

        //when
        Long saveCrewId = crewService.createCrew(createCrewDto, userEmail);

        //then
        assertThat(saveCrewId).isNotNull();
    }

    @Test
    @DisplayName("크루 생성시 중복되는 크루명이 존재할때")
    void createCrew_fail_DuplicateCrewName() {
        //given
        String userEmail = saveUser();
        CreateCrewDto createCrewDto1 = new CreateCrewDto("테스트크루");
        CreateCrewDto createCrewDto2 = new CreateCrewDto("테스트크루");

        crewService.createCrew(createCrewDto1, userEmail);

        //when
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> crewService.createCrew(createCrewDto2, userEmail));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 크루이름입니다.");
    }


    @Test
    @DisplayName("크루 생성시 사용자가 존재하지 않을때")
    void createCrew_fail_NotExistUser() {
        //given
        CreateCrewDto createCrewDto = new CreateCrewDto("테스트크루");
        String userEmail = "존재하지않는사용자이메일";

        //when
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> crewService.createCrew(createCrewDto, userEmail));

        //then
        assertThat(e.getMessage()).isEqualTo("존재하지 않는 사용자 입니다.");
    }


    @Test
    @DisplayName("크루 목록 전체 조회")
    void readCrewList() {
        //given
        String userEmail = saveUser();
        crewService.createCrew(new CreateCrewDto("테스트크루1"), userEmail);
        crewService.createCrew(new CreateCrewDto("테스트크루2"), userEmail);
        crewService.createCrew(new CreateCrewDto("테스트크루3"), userEmail);
        crewService.createCrew(new CreateCrewDto("테스트크루4"), userEmail);
        crewService.createCrew(new CreateCrewDto("테스트크루5"), userEmail);
        int page = 0;
        int size = 10;

        //when
        List<ReadCrewDto> readCrewList = crewService.readCrewList(page, size);

        //then
        assertThat(readCrewList.size()).isEqualTo(5);
    }


    @Test
    @DisplayName("크루 목록 이름으로 조회")
    void readCrewList_name(){
        //given
        String userEmail = saveUser();
        crewService.createCrew(new CreateCrewDto("테스트크루1"), userEmail);
        crewService.createCrew(new CreateCrewDto("크루2"), userEmail);
        crewService.createCrew(new CreateCrewDto("테스트크루3"), userEmail);
        crewService.createCrew(new CreateCrewDto("크루4"), userEmail);
        crewService.createCrew(new CreateCrewDto("테스트크루5"), userEmail);
        int page = 0;
        int size = 10;

        //when
        List<ReadCrewDto> readCrewList = crewService.readCrewListByName(page, size, "테스트");

        //then
        assertThat(readCrewList.size()).isEqualTo(3);
    }


    @Test
    @DisplayName("크루 아이디로 상세조회")
    void readCrewDetail_id(){
        //given
        String userEmail = saveUser();
        Long id = crewService.createCrew(new CreateCrewDto("테스트크루"), userEmail);

        //when
        ReadCrewDetailDto readCrewDetailDto = crewService.readCrewDetail(id);

        //then
        assertThat(readCrewDetailDto.getName()).isEqualTo("테스트크루");
    }

    @Test
    @DisplayName("크루 아이디로 상세조회시 결과값이 존재하지 않을때")
    void readCrewDetail_id_fail_NotExistCrew() {
        //given

        //when
        IllegalStateException e = assertThrows(IllegalStateException.class, () ->crewService.readCrewDetail(999L));

        //then
        assertThat(e.getMessage()).isEqualTo("존재하지 않는 크루 입니다.");
    }

    @Test
    @DisplayName("크루 수정하기")
    void updateCrew(){
        //given
        String userEmail = saveUser();
        Long id = crewService.createCrew(new CreateCrewDto("테스트크루"), userEmail);

        UpdateCrewDto updateCrewDto = new UpdateCrewDto(id, "바뀐크루이름");

        //when
        crewService.updateCrew(updateCrewDto);

        //then
        ReadCrewDetailDto crewDetailDto = crewService.readCrewDetail(id);
        assertThat(crewDetailDto.getName()).isEqualTo("바뀐크루이름");

    }


    @Test
    @DisplayName("크루 수정시 아이디가 없으면")
    void updateCrew_fail_IdIsNull(){
        //given
        UpdateCrewDto updateCrewDto = new UpdateCrewDto(null, "바뀐크루이름");

        //when
        IllegalStateException e = assertThrows(IllegalStateException.class, () ->crewService.updateCrew(updateCrewDto));

        //then
        assertThat(e.getMessage()).isEqualTo("존재하지 않는 크루 입니다.");
    }


    @Test
    @DisplayName("크루 수정시 존재하는 크루명으로 바꾸면")
    void updateCrew_fail_alreadyExist(){
        //given
        String userEmail = saveUser();
        crewService.createCrew(new CreateCrewDto("존재하는이름"), userEmail);
        Long id = crewService.createCrew(new CreateCrewDto("테스트크루"), userEmail);

        UpdateCrewDto updateCrewDto = new UpdateCrewDto(id, "존재하는이름");

        //when
        IllegalStateException e = assertThrows(IllegalStateException.class, () ->crewService.updateCrew(updateCrewDto));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 크루이름입니다.");

    }


    @Test
    @DisplayName("크루 삭제")
    void deleteCrew(){
        //given
        String userEmail = saveUser();
        Long id = crewService.createCrew(new CreateCrewDto("테스트크루"), userEmail);

        //when
        crewService.deleteCrew(id);

        //then
        IllegalStateException e = assertThrows(IllegalStateException.class, () ->crewService.readCrewDetail(id));
        assertThat(e.getMessage()).isEqualTo("존재하지 않는 크루 입니다.");

    }





}