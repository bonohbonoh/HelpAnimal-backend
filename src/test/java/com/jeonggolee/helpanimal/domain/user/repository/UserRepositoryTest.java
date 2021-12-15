package com.jeonggolee.helpanimal.domain.user.repository;

import com.jeonggolee.helpanimal.domain.crew.domain.Crew;
import com.jeonggolee.helpanimal.domain.user.entity.User;
import com.jeonggolee.helpanimal.domain.user.query.UserSearchSpecification;
import com.jeonggolee.helpanimal.domain.user.util.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserSearchSpecification userSearchSpecification;

    private static final Role GUEST = Role.GUEST;
    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "test password";
    private static final String NICKNAME = "test nickname";
    private static final String IMAGE = "test image";
    private static final String NAME = "홍길동";

    @Test
    @DisplayName("유저 생성 테스트")
    public void insertTest() {

        //given
        User user = User.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .nickname(NICKNAME)
                .profileImage(IMAGE)
                .role(GUEST)
                .build();

        //when
        User saveUser = userRepository.save(user);

        //then
        assertThat(saveUser.getEmail()).isEqualTo(EMAIL);
        assertThat(saveUser.getPassword()).isEqualTo(PASSWORD);
        assertThat(saveUser.getName()).isEqualTo(NAME);
        assertThat(saveUser.getNickname()).isEqualTo(NICKNAME);
        assertThat(saveUser.getProfileImage()).isEqualTo(IMAGE);
        assertThat(saveUser.getRole()).isEqualTo(GUEST);
    }

    @Test
    @DisplayName("유저 조회 테스트")
    public void readTest() {

        //given
        User user = User.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .nickname(NICKNAME)
                .profileImage(IMAGE)
                .role(GUEST)
                .build();
        User saveUser = userRepository.save(user);

        //when
        Long userId = saveUser.getUserId();

        //then
        User readUser = userRepository.getById(userId);
        assertThat(readUser.getEmail()).isEqualTo(EMAIL);
        assertThat(readUser.getPassword()).isEqualTo(PASSWORD);
        assertThat(readUser.getName()).isEqualTo(NAME);
        assertThat(readUser.getNickname()).isEqualTo(NICKNAME);
        assertThat(readUser.getProfileImage()).isEqualTo(IMAGE);
        assertThat(readUser.getRole()).isEqualTo(GUEST);
    }

    @Test
    @DisplayName("유저 수정 테스트")
    public void updateTest() {
        //given
        User user = User.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .nickname(NICKNAME)
                .profileImage(IMAGE)
                .role(GUEST)
                .build();
        User saveUser = userRepository.save(user);

        //when
        saveUser.updateNickname("update nickname");
        saveUser.updatePassword("update password");
        saveUser.updateProfileImage("update image");
        userRepository.save(saveUser);

        //then
        User updateUser = userRepository.getById(user.getUserId());
        assertThat(updateUser.getPassword()).isEqualTo("update password");
        assertThat(updateUser.getNickname()).isEqualTo("update nickname");
        assertThat(updateUser.getProfileImage()).isEqualTo("update image");

    }

    @Test
    @DisplayName("유저 삭제 테스트")
    public void deleteTest() {
        //given
        User user = User.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .nickname(NICKNAME)
                .profileImage(IMAGE)
                .role(GUEST)
                .build();
        User saveUser = userRepository.save(user);

        //when
        Long deleteUserId = saveUser.getUserId();
        userRepository.deleteById(deleteUserId);

        //then
        Optional<User> deleteUser = userRepository.findById(deleteUserId);
        assertThat(deleteUser.isPresent()).isFalse();
    }

    @Test
    @DisplayName("유저 검색 테스트")
    public void searchUserTest() {

        //given
        User user = User.builder()
                .email(EMAIL + "search")
                .password(PASSWORD)
                .name(NAME)
                .nickname(NICKNAME + "search")
                .profileImage(IMAGE)
                .role(GUEST)
                .build();

        //when
        userRepository.save(user);

        //then
        Optional<User> findUserEmail = userRepository.findOne(userSearchSpecification.searchWithEmail(EMAIL));
        assertThat(findUserEmail.isPresent()).isTrue();
        Optional<User> findUserNickname = userRepository.findOne(userSearchSpecification.searchWithNickname(NICKNAME));
        assertThat(findUserNickname.isPresent()).isTrue();
    }

    @Test
    @DisplayName("검색한 유저가 없을 경우 테스트")
    public void cantSearchUserTest() {

        //given
        User notSearchUser = User.builder()
                .email("not search" + EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .nickname("not search" + NICKNAME)
                .profileImage(IMAGE)
                .role(GUEST)
                .build();

        //when
        userRepository.save(notSearchUser);

        //then
        Optional<User> findUserEmail = userRepository.findOne(userSearchSpecification.searchWithEmail(EMAIL));
        assertThat(findUserEmail.isPresent()).isFalse();
        Optional<User> findUserNickname = userRepository.findOne(userSearchSpecification.searchWithNickname(NICKNAME));
        assertThat(findUserNickname.isPresent()).isFalse();
    }

}
