package com.jeonggolee.helpanimal.domain.user.service;

import com.jeonggolee.helpanimal.common.jwt.JwtTokenProvider;
import com.jeonggolee.helpanimal.domain.user.dto.UserLoginDto;
import com.jeonggolee.helpanimal.domain.user.dto.UserSignupDto;
import com.jeonggolee.helpanimal.domain.user.entity.User;
import com.jeonggolee.helpanimal.domain.user.query.UserSearchSpecification;
import com.jeonggolee.helpanimal.domain.user.repository.UserRepository;
import com.jeonggolee.helpanimal.domain.user.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserSearchSpecification userSearchSpecification;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider provider;

    public boolean signUpUser(UserSignupDto dto) throws Exception {
        String encodePassword = passwordEncoder.encode(dto.getPassword());
        dto.PasswordEncoding(encodePassword);
        Optional<User> user = userRepository.findOne(userSearchSpecification.searchWithEmailEqual(dto.getEmail()));
        if (user.isPresent()) {
            throw new DuplicateKeyException("이미 존재하는 유저입니다.");
        }
        String email = userRepository.save(dto.toEntity()).getEmail();
        if (email != null && !email.equals("")) {
            return true;
        }
        return false;
    }

    public String loginUser(UserLoginDto dto) throws Exception {
        User user = userRepository.findOne(userSearchSpecification.searchWithEmailEqual(dto.getEmail())).get();
        boolean isMatchingPassword = passwordEncoder.matches(dto.getPassword(), user.getPassword());
        if (isMatchingPassword) {
            return provider.generateToken(dto.getEmail(), Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString())));
        }
        throw new RuntimeException("비밀번호가 틀립니다.");

    }

}
