package com.jeonggolee.helpanimal.domain.recruitment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeonggolee.helpanimal.domain.recruitment.dto.request.RecruitmentApplicationRequestDto;
import com.jeonggolee.helpanimal.domain.recruitment.dto.response.RecruitmentApplicationDetailDto;
import com.jeonggolee.helpanimal.domain.recruitment.dto.response.RecruitmentApplicationSearchDto;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentApplicationStatus;
import com.jeonggolee.helpanimal.domain.recruitment.service.RecruitmentRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class RecruitmentRequestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private RecruitmentRequestService recruitmentRequestService;


    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }

    @Test
    @WithMockUser(username = "test1@naver.com", roles = {"USER"})
    void 참여신청_등록() throws Exception {
        RecruitmentApplicationRequestDto dto = RecruitmentApplicationRequestDto.builder()
                .email("test1@test.com")
                .comment("comment")
                .recruitmentId(1L)
                .build();

        when(recruitmentRequestService.requestRecruitment(any())).thenReturn(1L);

        String content = objectMapper.writeValueAsString(dto);
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/recruitments/requests")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "test1@naver.com", roles = {"USER"})
    void 참여신청_삭제() throws Exception {
        doNothing().when(recruitmentRequestService).deleteRecruitmentApplication(anyLong());
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/recruitments/requests/"+1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test1@naver.com", roles = {"USER"})
    void 참여신청내역_유저로_조회_페이징() throws Exception {

        when(recruitmentRequestService.findRecruitmentApplicationByUser(any(),anyLong()))
                .thenReturn(RecruitmentApplicationSearchDto.builder()
                        .numberOfElements(0)
                        .totalElements(10L)
                        .data(new ArrayList<>())
                        .build());

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/recruitments/requests/user/1?page=0&size=10")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(0))
                .andExpect(jsonPath("$.totalElements").value(10L));
    }

    @Test
    @WithMockUser(username = "test1@naver.com", roles = {"USER"})
    void 참여신청내역_공고로_조회_페이징() throws Exception {

        when(recruitmentRequestService.findRecruitmentApplicationByRecruitment(any(),anyLong()))
                .thenReturn(RecruitmentApplicationSearchDto.builder()
                        .numberOfElements(0)
                        .totalElements(10L)
                        .data(new ArrayList<>())
                        .build());

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/recruitments/1/requests?page=0&size=10")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(0))
                .andExpect(jsonPath("$.totalElements").value(10L));

    }

    @Test
    @WithMockUser(username = "test1@naver.com", roles = {"USER"})
    void 공고_상세조회() throws Exception {

        when(recruitmentRequestService.findById(anyLong()))
                .thenReturn(RecruitmentApplicationDetailDto.builder()
                        .id(1L)
                        .recruitmentId(1L)
                        .recruitmentName("TEST 공고")
                        .email("test1@test.com")
                        .comment("신청합니다")
                        .status(RecruitmentApplicationStatus.REQUEST)
                        .build());

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/recruitments/requests/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.recruitmentId").value(1L))
                .andExpect(jsonPath("$.recruitmentName").value("TEST 공고"))
                .andExpect(jsonPath("$.email").value("test1@test.com"))
                .andExpect(jsonPath("$.comment").value("신청합니다"))
                .andExpect(jsonPath("$.status").value("REQUEST"));
    }
}
