package com.jeonggolee.helpanimal;

import com.jeonggolee.helpanimal.domain.recruitment.entity.Animal;
import com.jeonggolee.helpanimal.domain.recruitment.repository.AnimalRepository;
import com.jeonggolee.helpanimal.domain.user.dto.UserSignupDto;
import com.jeonggolee.helpanimal.domain.user.entity.User;
import com.jeonggolee.helpanimal.domain.user.repository.UserRepository;
import com.jeonggolee.helpanimal.domain.user.service.UserService;
import com.jeonggolee.helpanimal.domain.user.util.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("local")
public class InitLocalTestData {
    private final AnimalRepository animalRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private static final Role ROLE = Role.USER;
    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "test_password";
    private static final String NICKNAME = "test nickname";
    private static final String IMAGE = "test image";
    private static final String NAME = "홍길동";

    @PostConstruct
    public void init() throws Exception {
        log.info("init");
        userService.signUpUser(UserSignupDto.builder()
                .name(NAME)
                .role(ROLE)
                .email(EMAIL)
                .password(PASSWORD)
                .nickname(NICKNAME)
                .profileImag(IMAGE)
                .build());

        User user = userRepository.getById(1L);
        Animal dog = Animal.builder()
                .name("DOG")
                .user(user)
                .build();

        Animal cat = Animal.builder()
                .name("CAT")
                .user(user)
                .build();

        animalRepository.saveAll(List.of(dog, cat));
    }
}
