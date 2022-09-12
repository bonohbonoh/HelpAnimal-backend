package com.jeonggolee.helpanimal.domain.user.repository;

import com.jeonggolee.helpanimal.domain.user.entity.UserEntity;
import com.jeonggolee.helpanimal.domain.user.query.UserSpecification;
import com.jeonggolee.helpanimal.domain.user.util.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("local")
public class UserEntityRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSpecification userSpecification;

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
        UserEntity userEntity = UserEntity.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .nickname(NICKNAME)
                .profileImage(IMAGE)
                .role(GUEST)
                .build();

        //when
        UserEntity saveUserEntity = userRepository.save(userEntity);

        //then
        assertThat(saveUserEntity.getEmail()).isEqualTo(EMAIL);
        assertThat(saveUserEntity.getPassword()).isEqualTo(PASSWORD);
        assertThat(saveUserEntity.getName()).isEqualTo(NAME);
        assertThat(saveUserEntity.getNickname()).isEqualTo(NICKNAME);
        assertThat(saveUserEntity.getProfileImage()).isEqualTo(IMAGE);
        assertThat(saveUserEntity.getRole()).isEqualTo(GUEST);
    }

    @Test
    @DisplayName("유저 조회 테스트")
    public void readTest() {

        //given
        UserEntity userEntity = UserEntity.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .nickname(NICKNAME)
                .profileImage(IMAGE)
                .role(GUEST)
                .build();
        UserEntity saveUserEntity = userRepository.save(userEntity);

        //when
        Long userId = saveUserEntity.getUserId();

        //then
        UserEntity readUserEntity = userRepository.getById(userId);
        assertThat(readUserEntity.getEmail()).isEqualTo(EMAIL);
        assertThat(readUserEntity.getPassword()).isEqualTo(PASSWORD);
        assertThat(readUserEntity.getName()).isEqualTo(NAME);
        assertThat(readUserEntity.getNickname()).isEqualTo(NICKNAME);
        assertThat(readUserEntity.getProfileImage()).isEqualTo(IMAGE);
        assertThat(readUserEntity.getRole()).isEqualTo(GUEST);
    }

    @Test
    @DisplayName("유저 수정 테스트")
    public void updateTest() {
        //given
        UserEntity userEntity = UserEntity.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .nickname(NICKNAME)
                .profileImage(IMAGE)
                .role(GUEST)
                .build();
        UserEntity saveUserEntity = userRepository.save(userEntity);

        //when
        saveUserEntity.updateNickname("update nickname");
        saveUserEntity.updatePassword("update password");
        saveUserEntity.updateProfileImage("update image");
        userRepository.save(saveUserEntity);

        //then
        UserEntity updateUserEntity = userRepository.getById(userEntity.getUserId());
        assertThat(updateUserEntity.getPassword()).isEqualTo("update password");
        assertThat(updateUserEntity.getNickname()).isEqualTo("update nickname");
        assertThat(updateUserEntity.getProfileImage()).isEqualTo("update image");

    }

    @Test
    @DisplayName("유저 삭제 테스트")
    public void deleteTest() {
        //given
        UserEntity userEntity = UserEntity.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .nickname(NICKNAME)
                .profileImage(IMAGE)
                .role(GUEST)
                .build();
        UserEntity saveUserEntity = userRepository.save(userEntity);

        //when
        Long deleteUserId = saveUserEntity.getUserId();
        userRepository.deleteById(deleteUserId);

        //then
        Optional<UserEntity> deleteUser = userRepository.findById(deleteUserId);
        assertThat(deleteUser.isPresent()).isFalse();
    }

    @Test
    @DisplayName("유저 검색 테스트")
    public void searchUserTest() {

        //given
        UserEntity userEntity = UserEntity.builder()
                .email(EMAIL + "search")
                .password(PASSWORD)
                .name(NAME)
                .nickname(NICKNAME + "search")
                .profileImage(IMAGE)
                .role(GUEST)
                .build();

        //when
        userRepository.save(userEntity);

        //then
        Optional<UserEntity> findUserEmail = userRepository.findOne(userSpecification.searchWithEmail(EMAIL));
        assertThat(findUserEmail.isPresent()).isTrue();
        Optional<UserEntity> findUserNickname = userRepository.findOne(userSpecification.searchWithNickname(NICKNAME));
        assertThat(findUserNickname.isPresent()).isTrue();
    }

    @Test
    @DisplayName("검색한 유저가 없을 경우 테스트")
    public void cantSearchUserTest() {

        //given
        UserEntity notSearchUserEntity = UserEntity.builder()
                .email("not search" + EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .nickname("not search" + NICKNAME)
                .profileImage(IMAGE)
                .role(GUEST)
                .build();

        //when
        userRepository.save(notSearchUserEntity);

        //then
        Optional<UserEntity> findUserEmail = userRepository.findOne(userSpecification.searchWithEmail(EMAIL));
        assertThat(findUserEmail.isPresent()).isFalse();
        Optional<UserEntity> findUserNickname = userRepository.findOne(userSpecification.searchWithNickname(NICKNAME));
        assertThat(findUserNickname.isPresent()).isFalse();
    }

}
