package com.jeonggolee.helpanimal.domain.user.dto;

import com.jeonggolee.helpanimal.domain.user.entity.UserEntity;
import java.util.Collection;
import java.util.Collections;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class CustomUserDetails extends User{

    private Long id;
    private String email;
    private String name;
    private String nickname;

    public CustomUserDetails(UserEntity user) {
        super(user.getEmail(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority(
            user.getRole().getKey())));
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.nickname = user.getNickname();
    }
}
