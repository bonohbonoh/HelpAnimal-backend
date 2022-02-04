package com.jeonggolee.helpanimal.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeonggolee.helpanimal.common.dto.JwtTokenDto;
import com.jeonggolee.helpanimal.common.exception.UserNotFoundException;
import com.jeonggolee.helpanimal.common.jwt.JwtTokenProvider;
import com.jeonggolee.helpanimal.domain.user.dto.UserLoginDto;
import com.jeonggolee.helpanimal.domain.user.dto.UserSignupDto;
import com.jeonggolee.helpanimal.domain.user.entity.User;
import com.jeonggolee.helpanimal.domain.user.query.UserSpecification;
import com.jeonggolee.helpanimal.domain.user.repository.UserRepository;
import com.jeonggolee.helpanimal.domain.user.util.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@Transactional
@ActiveProfiles("local")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class UserControllerTest {

    private final Role GUEST = Role.GUEST;
    private final String EMAIL = "test@naver.com";
    private final String PASSWORD = "password";
    private final String NICKNAME = "nickname";
    private final String IMAGE = "image";
    private final String NAME = "홍길동";
    private final String URL = "/api/v1/user/";
    private final String AUTH_URL = "/api/v1/user/auth/";
    private final String EMAIL_VERIFY_URL = "https://localtest:/authUser/Email?=email";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSpecification searchSpecification;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider provider;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public void settingMvcBuilder() {
        mvc = MockMvcBuilders
                .standaloneSetup(userController)
                .addFilter(new CharacterEncodingFilter("utf-8", true))
                .build();
    }

    @BeforeAll
    public void initUser() {
        User user = User.builder()
                .email(EMAIL)
                .password(passwordEncoder.encode(PASSWORD))
                .nickname(NICKNAME)
                .profileImage(IMAGE)
                .name(NAME)
                .role(GUEST)
                .build();
        userRepository.save(user);
    }

    public void updateUserUrl() {
        User user = findUser();
        user.updateUrl(EMAIL_VERIFY_URL);
        userRepository.save(user);
    }

    public User findUser() {
        return userRepository.findOne(searchSpecification.searchWithEmailEqual(EMAIL))
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 회원입니다."));
    }

    @Test
    public void signUpControllerTest() throws Exception {
        userRepository.deleteAll();

        //given
        String content = objectMapper.writeValueAsString(new UserSignupDto(EMAIL, PASSWORD, NAME, NICKNAME, IMAGE, GUEST));

        //when
        mvc.perform(
                        MockMvcRequestBuilders.post(URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                                .accept(MediaType.APPLICATION_JSON)
                )
                //then
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print())
                .andReturn();

    }

    @Test
    public void loginControllerTest() throws Exception {

        //given
        String content = objectMapper.writeValueAsString(new UserLoginDto(EMAIL, PASSWORD));

        //when
        MvcResult result = mvc.perform(
                        MockMvcRequestBuilders.post(URL + "login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                                .accept(MediaType.APPLICATION_JSON)
                )
                //then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();

        JwtTokenDto token = objectMapper.readValue(result.getResponse().getContentAsString(), JwtTokenDto.class);
        assertThat(provider.validateToken(token.getToken())).isTrue();

    }

    @Test
    @WithUserDetails(EMAIL)
    public void readUserInfoControllerTest() throws Exception {

        //given
        String password = PASSWORD;

        //when
        mvc.perform(
                        MockMvcRequestBuilders.get(AUTH_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("password", password)
                                .accept(MediaType.APPLICATION_JSON)
                )

                //then
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    //given
    @WithUserDetails(EMAIL)
    public void emailSendControllerTest() throws Exception {

        //when
        MvcResult result = mvc.perform(
                        MockMvcRequestBuilders.post(AUTH_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                //then
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())

                .andReturn();

        User user = findUser();

        assertThat(user.getUrl()).isNotNull();
        assertThat(result.getResponse().getContentAsString()).isEqualTo(user.getUrl());

    }

    @Test
    @WithUserDetails(EMAIL)
    public void emailVerifyControllerTest() throws Exception {
        updateUserUrl();

        //given
        String emailVerifyUrl = EMAIL_VERIFY_URL;

        //when
        MvcResult result = mvc.perform(
                        MockMvcRequestBuilders.post(AUTH_URL + "email-verify")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(emailVerifyUrl)
                                .accept(MediaType.APPLICATION_JSON)
                )
                //then
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())

                .andReturn();

        User user = findUser();
        assertThat(user.getUrl()).isNotNull();
        assertThat(user.getRole()).isEqualTo(Role.USER);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(Role.USER.toString());

    }

}
