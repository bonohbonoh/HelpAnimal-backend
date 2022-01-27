package com.jeonggolee.helpanimal.domain.user.service;

import com.jeonggolee.helpanimal.common.dto.JwtTokenDto;
import com.jeonggolee.helpanimal.common.exception.UserNotFoundException;
import com.jeonggolee.helpanimal.common.jwt.JwtTokenProvider;
import com.jeonggolee.helpanimal.domain.user.dto.UserInfoReadDto;
import com.jeonggolee.helpanimal.domain.user.dto.UserLoginDto;
import com.jeonggolee.helpanimal.domain.user.dto.UserSignupDto;
import com.jeonggolee.helpanimal.domain.user.entity.User;
import com.jeonggolee.helpanimal.domain.user.query.UserSearchSpecification;
import com.jeonggolee.helpanimal.domain.user.repository.UserRepository;
import com.jeonggolee.helpanimal.domain.user.util.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class UserServiceTest {

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
    private UserSearchSpecification searchSpecification;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider provider;

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

    public User findUser() {
        return userRepository.findOne(searchSpecification.searchWithEmailEqual(EMAIL))
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 회원입니다."));

    }

    @Test
    public void signUpUserServiceTest() {
        userRepository.deleteAll();

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
    @WithUserDetails(EMAIL)
    public void readUserInfoServiceTest() {

        //given
        String password = PASSWORD;

        //when
        UserInfoReadDto dto = userService.userInfoReadDto(password);

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
        User user = findUser();

        //when
        String url = userService.sendEmail();

        //then
        assertThat(user.getUrl()).isNotNull();
        assertThat(user.getUrl()).isEqualTo(url);
    }

    @Test
    @WithUserDetails(EMAIL)
    public void authUserServiceTest() {

        //given
        String url = userService.sendEmail();

        //when
        String role = userService.authEmail(url);

        //then
        User user = findUser();

        assertThat(user.getUrl()).isEqualTo(url);
        assertThat(user.getRole().toString()).isEqualTo(role);
    }


}
