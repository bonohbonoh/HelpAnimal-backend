package com.jeonggolee.helpanimal.domain.recruitment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeonggolee.helpanimal.domain.recruitment.dto.request.RecruitmentRegistDto;
import com.jeonggolee.helpanimal.domain.recruitment.dto.request.RecruitmentUpdateDto;
import com.jeonggolee.helpanimal.domain.recruitment.entity.Animal;
import com.jeonggolee.helpanimal.domain.recruitment.entity.Recruitment;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentMethod;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentType;
import com.jeonggolee.helpanimal.domain.recruitment.repository.AnimalRepository;
import com.jeonggolee.helpanimal.domain.recruitment.repository.RecruitmentRepository;
import com.jeonggolee.helpanimal.domain.user.entity.User;
import com.jeonggolee.helpanimal.domain.user.repository.UserRepository;
import com.jeonggolee.helpanimal.domain.user.util.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("local")
public class RecruitmentControllerTest {
    private final String NAME = "테스트공고";
    private final String EMAIL = "test1@naver.com";
    private final String CONTENT = "본문";
    private final String ANIMAL = "DOG_";
    private final int PARTICIPANT = 10;
    private final RecruitmentMethod RECRUITMENT_METHOD = RecruitmentMethod.CHOICE;
    private final RecruitmentType RECRUITMENT_TYPE = RecruitmentType.FREE;
    private static Long RECRUITMENT_ID;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private RecruitmentRepository recruitmentRepository;

    @Autowired
    private WebApplicationContext wac;


    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }


    @BeforeAll
    public void 유저_동물_등록() {
        User user = initUser();
        Animal animal = initAnimal(user);
        initRecruitment(user, animal);
    }

    private User initUser() {
        User user = User.builder()
                .name("테스트")
                .email(EMAIL)
                .password("pass")
                .nickname("닉네임")
                .profileImage("image")
                .role(Role.GUEST)
                .build();
        return userRepository.save(user);
    }

    private Animal initAnimal(User user) {
        return animalRepository.save(Animal.builder()
                .name(ANIMAL)
                .user(user)
                .build()
        );
    }

    private void initRecruitment(User user, Animal animal) {
        RECRUITMENT_ID = recruitmentRepository.save(
                Recruitment.builder()
                        .name(NAME)
                        .user(user)
                        .content(CONTENT)
                        .animal(animal)
                        .participant(PARTICIPANT)
                        .recruitmentMethod(RECRUITMENT_METHOD)
                        .recruitmentType(RECRUITMENT_TYPE)
                        .build()).getId();
    }

    @Test
    @WithMockUser(username = "test1@naver.com", roles = {"USER"})
    public void 공고등록() throws Exception {
        RecruitmentRegistDto dto = RecruitmentRegistDto.builder()
                .name("공고등록_이름")
                .email(EMAIL)
                .content(CONTENT)
                .animal(ANIMAL)
                .participant(PARTICIPANT)
                .recruitmentMethod(RECRUITMENT_METHOD)
                .recruitmentType(RECRUITMENT_TYPE)
                .build();

        String content = objectMapper.writeValueAsString(dto);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/recruitment")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "test1@naver.com", roles = {"USER"})
    public void 공고페이징() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/recruitment?page=0&size=10&sort=id,desc")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test1@naver.com", roles = {"USER"})
    public void 공고수정() throws Exception {
        RecruitmentUpdateDto dto = RecruitmentUpdateDto.builder()
                .name("공고명_수정")
                .content("공고내용_수정")
                .participant(3)
                .imageUrl("UPDATE_URL")
                .build();

        String content = objectMapper.writeValueAsString(dto);

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/recruitment/"+RECRUITMENT_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test1@naver.com", roles = {"USER"})
    public void 공고_상세조회() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/recruitment/" + RECRUITMENT_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test1@naver.com", roles = {"USER"})
    public void 공고삭제() throws Exception{
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/recruitment/" + RECRUITMENT_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
