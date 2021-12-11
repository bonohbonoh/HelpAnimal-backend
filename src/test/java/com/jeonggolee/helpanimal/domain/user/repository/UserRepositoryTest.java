package com.jeonggolee.helpanimal.domain.user.repository;

import com.jeonggolee.helpanimal.domain.user.entity.User;
import com.jeonggolee.helpanimal.domain.user.util.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private Role guest = Role.GUEST;

    @Test
    @DisplayName("insert database")
    @Transactional
    public void insertTest() {
        IntStream.rangeClosed(0, 10).forEach(i -> {
            //given
            User user = User.builder()
                    .email("test email" + i)
                    .password("test password" + i)
                    .name("n" + i)
                    .nickName("test nick name" + i)
                    .profileImage("basic image" + i)
                    .role(guest)
                    .build();

            //when
            User insertUser = userRepository.save(user);

            //then
            assertThat(insertUser.getEmail()).isEqualTo("test email" + i);
            assertThat(insertUser.getPassword()).isEqualTo("test password" + i);
            assertThat(insertUser.getName()).isEqualTo("n" + i);
            assertThat(insertUser.getNickName()).isEqualTo("test nick name" + i);
            assertThat(insertUser.getProfileImage()).isEqualTo("basic image" + i);
            assertThat(insertUser.getRole()).isEqualTo(guest);
        });
    }

    @Test
    @DisplayName("read database")
    public void readTest() {
        IntStream.rangeClosed(0, 10).forEach(i -> {
            //given
            User user = User.builder()
                    .email("test email" + i)
                    .password("test password" + i)
                    .name("n" + i)
                    .nickName("test nick name" + i)
                    .profileImage("basic image" + i)
                    .role(guest)
                    .build();
            userRepository.save(user);

            //when
            List<User> UserList = userRepository.findAll();

            //then
            assertThat(UserList.get(i).getEmail()).isEqualTo("test email" + i);
            assertThat(UserList.get(i).getPassword()).isEqualTo("test password" + i);
            assertThat(UserList.get(i).getName()).isEqualTo("n" + i);
            assertThat(UserList.get(i).getNickName()).isEqualTo("test nick name" + i);
            assertThat(UserList.get(i).getProfileImage()).isEqualTo("basic image" + i);
            assertThat(UserList.get(i).getRole()).isEqualTo(guest);
        });
    }

    @Test
    @DisplayName("update database")
    public void updateTest() {

        IntStream.rangeClosed(0, 10).forEach(i -> {
            //given
            User user = User.builder()
                    .email("test email" + i)
                    .password("test password" + i)
                    .name("n" + i)
                    .nickName("test nick name" + i)
                    .profileImage("basic image" + i)
                    .role(guest)
                    .build();
            userRepository.save(user);
        });

        LongStream.rangeClosed(1, 10).forEach(i -> {

            //when
            User userUpdateList = userRepository.findById(i).get();
            userUpdateList.updateNickName("update nickname" + i);
            userUpdateList.updateProfileImage("update image" + i);
            userUpdateList.updatePassWord("update password" + i);
            userRepository.save(userUpdateList);

            //then
            assertThat(userUpdateList.getNickName()).isEqualTo("update nickname" + i);
            assertThat(userUpdateList.getProfileImage()).isEqualTo("update image" + i);
            assertThat(userUpdateList.getPassword()).isEqualTo("update password" + i);
        });

    }

    @Test
    @DisplayName("delete database")
    public void deleteTest() {
        //given
        IntStream.rangeClosed(0, 10).forEach(i -> {
            User user = User.builder()
                    .email("test email" + i)
                    .password("test password" + i)
                    .name("n" + i)
                    .nickName("test nick name" + i)
                    .profileImage("basic image" + i)
                    .role(guest)
                    .build();
            userRepository.save(user);
        });

        LongStream.rangeClosed(1, 10).forEach(i -> {

            //when
            userRepository.deleteById(i);

            //then
            Optional<User> user = userRepository.findById(i);
            assertThat(user.isPresent()).isFalse();

        });
    }
}
