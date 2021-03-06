package com.jeonggolee.helpanimal.domain.user.service;

import com.jeonggolee.helpanimal.domain.user.query.UserSpecification;
import com.jeonggolee.helpanimal.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserSpecification userSpecification;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.jeonggolee.helpanimal.domain.user.entity.
                User user = userRepository.findOne(userSpecification.searchWithEmailEqual(email))
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new User(user.getEmail(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority(user.getRole().getKey())));
    }

}
