package com.jeonggolee.helpanimal.domain.user.service;

import com.jeonggolee.helpanimal.common.exception.UserNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("local")
public class UserEntityServiceFailTest {
    private final Role GUEST = Role.GUEST;
    private final String EMAIL = "test@naver.com";
    private final String WRONG_EMAIL = "wrong@naver.com";
    private final String PASSWORD = "password";
    private final String WRONG_PASSWORD = "wrong_password";
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

    @Test
    public void duplicateUserServiceTest() {
        //given
        UserSignupDto dto = new UserSignupDto(EMAIL, PASSWORD, NAME, NICKNAME, IMAGE, GUEST);

        //when
        RuntimeException e = assertThrows(RuntimeException.class, () -> userService.signUpUser(dto));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 유저입니다.");
    }

    @Test
    public void wrongEmailUserLoginFailServiceTest() {

        //given
        UserLoginDto dto = new UserLoginDto(WRONG_EMAIL, PASSWORD);

        //when
        RuntimeException e = assertThrows(RuntimeException.class, () -> userService.loginUser(dto));

        //then
        assertThat(e.getMessage()).isEqualTo("존재하지 않는 회원입니다.");
    }

    @Test
    public void wrongPasswordUserLoginFailServiceTest() {

        //given
        UserLoginDto dto = new UserLoginDto(EMAIL, WRONG_PASSWORD);

        //when
        RuntimeException e = assertThrows(RuntimeException.class, () -> userService.loginUser(dto));

        //then
        assertThat(e.getMessage()).isEqualTo("잘못된 패스워드 입니다.");
    }


    @Test
    @WithUserDetails(EMAIL)
    public void authUserFailServiceTest() {

        //given
        String url = userService.sendEmail();
        String failUrl = "failUrl/wrongUrl";

        //when
        RuntimeException e = assertThrows(RuntimeException.class, () -> userService.authEmail(failUrl));

        //then
        UserEntity userEntity = userRepository.findOne(searchSpecification.searchWithEmailEqual(EMAIL))
                .orElseThrow(() -> new UserNotFoundException("유저가 존재하지 않습니다."));
        assertThat(userEntity.getUrl()).isNotNull();
        assertThat(url.equals(failUrl)).isFalse();
        assertThat(failUrl.equals(userEntity.getUrl())).isFalse();
        assertThat(e.getMessage()).isEqualTo("잘못된 Url 입니다.");
        assertThat(userEntity.getRole()).isEqualTo(GUEST);
    }

}
