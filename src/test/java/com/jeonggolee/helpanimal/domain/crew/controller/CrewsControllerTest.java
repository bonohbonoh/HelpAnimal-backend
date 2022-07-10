package com.jeonggolee.helpanimal.domain.crew.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeonggolee.helpanimal.domain.crew.dto.CreateCrewDto;
import com.jeonggolee.helpanimal.domain.crew.dto.UpdateCrewDto;
import com.jeonggolee.helpanimal.domain.crew.exception.handler.CrewExceptionHandler;
import com.jeonggolee.helpanimal.domain.crew.service.CrewService;
import com.jeonggolee.helpanimal.domain.user.entity.User;
import com.jeonggolee.helpanimal.domain.user.repository.UserRepository;
import com.jeonggolee.helpanimal.domain.user.util.Role;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.filter.CharacterEncodingFilter;


import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CrewsControllerTest {
    @Autowired
    MockMvc crewMvc;
    @Autowired
    CrewController crewController;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CrewService crewService;

    private static final String URL = "/api/v1";

    private static final String requesterEmail = "test@gmail.com";

    //빌더 세팅
    @BeforeAll
    public void settingMvcBuilder(){
        crewMvc = MockMvcBuilders
                .standaloneSetup(crewController)
                .setControllerAdvice(new CrewExceptionHandler())
                .addFilter(new CharacterEncodingFilter("utf-8", true))
                .build();
    }

    //사용자 생성
    @BeforeAll
    public void createUser(){
        User user = User.builder()
                .email(requesterEmail)
                .password("test")
                .name("테스트이름")
                .nickname("테스트별명")
                .profileImage("https://img.url.test")
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }

    @Test
    @WithUserDetails(requesterEmail)
    @DisplayName("크루 생성하기")
    void createCrew() throws Exception {
        //given
        CreateCrewDto createCrewDto = new CreateCrewDto("테스트크루");

        String requestBody = objectMapper.writeValueAsString(createCrewDto);

        //when
        MvcResult result = crewMvc.perform(
                MockMvcRequestBuilders.post(URL + "/crew")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())//.andExpect 사용시 assertThat 과 마찬가지로 테스트 가능함
                .andReturn();

        //then
        assertThat(result.getResponse()).isNotNull();
    }

    @Test
    @WithUserDetails(requesterEmail)
    @DisplayName("크루생성 실패 중복된 크루명 ")
    void createCrew_fail_duplicateCrewName() throws Exception {
        //given
        CreateCrewDto createCrewDto = new CreateCrewDto("테스트크루");

        String requestBody = objectMapper.writeValueAsString(createCrewDto);

        //when
        crewMvc.perform(
                        MockMvcRequestBuilders.post(URL + "/crew")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
        .andExpect(MockMvcResultMatchers.status().isOk());

        crewMvc.perform(
                        MockMvcRequestBuilders.post(URL + "/crew")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
        //then
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private Long createCrewList(){
        crewService.createCrew(new CreateCrewDto("크루 1"), requesterEmail);
        crewService.createCrew(new CreateCrewDto("크루 2"), requesterEmail);
        crewService.createCrew(new CreateCrewDto("크루 3"), requesterEmail);
        crewService.createCrew(new CreateCrewDto("크루짜잔 4"), requesterEmail);
        crewService.createCrew(new CreateCrewDto("크루짜잔 5"), requesterEmail);
        return crewService.createCrew(new CreateCrewDto("크루짜잔 6"), requesterEmail);
    }

    @Test
    @WithUserDetails(requesterEmail)
    @DisplayName("크루 전체 목록 조회")
    void readCrewList() throws Exception {
        //given
        createCrewList();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "0");
        params.add("size", "10");

        //when
        MvcResult result = crewMvc.perform(
                        MockMvcRequestBuilders.get(URL + "/crew").params(params)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //then
        JSONArray jsonResult = new JSONArray(result.getResponse().getContentAsString());
        assertThat(jsonResult.length()).isEqualTo(6);

    }

    @Test
    @WithUserDetails(requesterEmail)
    @DisplayName("크루 목록 이름으로 검색")
    void readCrewList_name() throws Exception {
        //given
        createCrewList();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "0");
        params.add("size", "10");
        params.add("name", "짜잔");

        //when
        MvcResult result = crewMvc.perform(
                        MockMvcRequestBuilders.get(URL + "/crew").params(params)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //then
        JSONArray jsonResult = new JSONArray(result.getResponse().getContentAsString());
        assertThat(jsonResult.length()).isEqualTo(3);
    }

    @Test
    @WithUserDetails(requesterEmail)
    @DisplayName("크루 아이디로 상세조회")
    void readCrew_id() throws Exception {
        //given
        Long id = createCrewList();

        //when
        MvcResult result = crewMvc.perform(
                        MockMvcRequestBuilders.get(URL + "/crew/"+id)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        //then
        JSONObject jsonResult = new JSONObject(result.getResponse().getContentAsString());
        assertThat(jsonResult.getLong("id")).isEqualTo(id);
    }

    @Test
    @WithUserDetails(requesterEmail)
    @DisplayName("크루 수정")
    void updateCrew() throws Exception {
        //given
        Long id = createCrewList();
        UpdateCrewDto updateCrewDto = new UpdateCrewDto(id, "바뀐이름");

        String requestBody = objectMapper.writeValueAsString(updateCrewDto);

        //when
        crewMvc.perform(
                        MockMvcRequestBuilders.put(URL + "/crew")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails(requesterEmail)
    @DisplayName("크루 삭제")
    void deleteCrew() throws Exception {
        //given
        Long id = createCrewList();

        //when
        crewMvc.perform(
                        MockMvcRequestBuilders.delete(URL + "/crew/" + id)
                )
        .andExpect(MockMvcResultMatchers.status().isOk());
    }
}