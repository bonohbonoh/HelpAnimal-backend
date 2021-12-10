package com.jeonggolee.helpanimal.domain.user;

import com.jeonggolee.helpanimal.domain.user.entity.User;
import com.jeonggolee.helpanimal.domain.user.repository.UserRepository;
import com.jeonggolee.helpanimal.domain.user.util.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
@SpringBootTest
public class UserDataBaseConnectionTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void repositoryTest() {
        //create
        Role guest = Role.GUEST;
        IntStream.rangeClosed(0, 10).forEach(i -> {
            User user = User.builder()
                    .email("test eamil" + i)
                    .password("test password" + i)
                    .name("" + i)
                    .nickName("test nick name" + i)
                    .profileImage("basic image" + i)
                    .role(guest)
                    .build();
            userRepository.save(user);
        });

        //read
        LongStream.rangeClosed(1, 10).forEach(i -> {
            Optional<User> user = userRepository.findById(i);
            System.out.println(user.toString());
        });

        //update
        LongStream.rangeClosed(0, 10).forEach(i -> {
            Optional<User> userUpdateList = userRepository.findById(i);
            userUpdateList.ifPresent(updateUser -> {
                User user = updateUser.update("update nickname" + i, "update image" + i, "update password" + i);
                userRepository.save(user);
            });
        });

        System.out.println("\n\n");

        //read
        LongStream.rangeClosed(1, 10).forEach(i -> {
            Optional<User> user = userRepository.findById(i);
            System.out.println(user.toString() + i);
        });

        //delete
        LongStream.rangeClosed(0, 10).forEach(i -> {
            Optional<User> user = userRepository.findById(i);
            user.ifPresent(deleteUser -> {
                userRepository.delete(deleteUser);
            });
        });

        //read
        Optional<User> user = userRepository.findById(1L);
        System.out.println(user.toString());
    }

}
