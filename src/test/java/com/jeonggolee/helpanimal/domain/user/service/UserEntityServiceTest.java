package com.jeonggolee.helpanimal.domain.user.service;

import com.jeonggolee.helpanimal.common.dto.JwtTokenDto;
import com.jeonggolee.helpanimal.common.exception.UserNotFoundException;
import com.jeonggolee.helpanimal.common.jwt.JwtTokenProvider;
import com.jeonggolee.helpanimal.domain.user.dto.UserInfoReadDto;
import com.jeonggolee.helpanimal.domain.user.dto.UserLoginDto;
import com.jeonggolee.helpanimal.domain.user.dto.UserSignupDto;
import com.jeonggolee.helpanimal.domain.user.entity.UserEntity;
import com.jeonggolee.helpanimal.domain.user.query.UserSpecification;
import com.jeonggolee.helpanimal.domain.user.repository.UserRepository;
import com.jeonggolee.helpanimal.domain.user.util.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("local")
public class UserEntityServiceTest {

    private final Role GUEST = Role.GUEST;
    private final String EMAIL = "test@naver.com";
    private final String PASSWORD = "password";
    private final String NICKNAME = "nickname";
    private final String IMAGE = "image";
    private final String NAME = "홍길동";

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSpecification searchSpecification;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider provider;

    @BeforeAll
    public void initUser() {
        UserEntity userEntity = UserEntity.builder()
                .email(EMAIL)
                .password(passwordEncoder.encode(PASSWORD))
                .nickname(NICKNAME)
                .profileImage(IMAGE)
                .name(NAME)
                .role(GUEST)
                .build();
        userRepository.save(userEntity);
    }

    public UserEntity findUser() {
        return userRepository.findOne(searchSpecification.searchWithEmailEqual(EMAIL))
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 회원입니다."));

    }

    @Test
    public void signUpUserServiceTest() {
        UserEntity userEntity = findUser();
        userRepository.delete(userEntity);

        //given
        UserSignupDto dto = new UserSignupDto(EMAIL, PASSWORD, NAME, NICKNAME, IMAGE, GUEST);

        //when
        String email = userService.signUpUser(dto);

        //then
        assertThat(email).isEqualTo(EMAIL);
        assertThat(dto.getPassword().equals(PASSWORD)).isFalse();
        assertThat(dto.getName()).isEqualTo(NAME);
        assertThat(dto.getNickname()).isEqualTo(NICKNAME);
        assertThat(dto.getRole()).isEqualTo(GUEST);
        assertThat(dto.getProfileImage()).isEqualTo(IMAGE);

    }

    @Test
    public void loginUserServiceTest() {

        //given
        UserLoginDto dto = new UserLoginDto(EMAIL, PASSWORD);

        //when
        JwtTokenDto tokenDto = userService.loginUser(dto);

        //then
        assertThat(provider.validateToken(tokenDto.getToken())).isTrue();
    }

    @Test
    //given
    @WithUserDetails(EMAIL)
    public void readUserInfoServiceTest() {

        //when
        UserInfoReadDto dto = userService.getUserInfo();

        //then
        assertThat(dto.getEmail()).isEqualTo(EMAIL);
        assertThat(dto.getName()).isEqualTo(NAME);
        assertThat(dto.getNickname()).isEqualTo(NICKNAME);
        assertThat(dto.getRole()).isEqualTo(GUEST);
        assertThat(dto.getProfileImage()).isEqualTo(IMAGE);
    }

    @Test
    @WithUserDetails(EMAIL)
    public void mailSendServiceTest() {

        //given
        UserEntity userEntity = findUser();

        //when
        String url = userService.sendEmail();

        //then
        assertThat(userEntity.getUrl()).isNotNull();
        assertThat(userEntity.getUrl()).isEqualTo(url);
    }

    @Test
    @WithUserDetails(EMAIL)
    public void authUserServiceTest() {

        //given
        String url = userService.sendEmail();

        //when
        String role = userService.authEmail(url);

        //then
        UserEntity userEntity = findUser();

        assertThat(userEntity.getUrl()).isEqualTo(url);
        assertThat(userEntity.getRole().toString()).isEqualTo(role);
    }


}
