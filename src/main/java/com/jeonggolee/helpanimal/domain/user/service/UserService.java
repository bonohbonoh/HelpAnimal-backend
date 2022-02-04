package com.jeonggolee.helpanimal.domain.user.service;

import com.jeonggolee.helpanimal.common.dto.JwtTokenDto;
import com.jeonggolee.helpanimal.common.exception.UserNotFoundException;
import com.jeonggolee.helpanimal.common.jwt.JwtTokenProvider;
import com.jeonggolee.helpanimal.domain.user.dto.UserInfoReadDto;
import com.jeonggolee.helpanimal.domain.user.dto.UserLoginDto;
import com.jeonggolee.helpanimal.domain.user.dto.UserSignupDto;
import com.jeonggolee.helpanimal.domain.user.entity.User;
import com.jeonggolee.helpanimal.domain.user.exception.auth.WrongAuthenticationUrlException;
import com.jeonggolee.helpanimal.domain.user.exception.login.WrongPasswordException;
import com.jeonggolee.helpanimal.domain.user.exception.signup.UserDuplicationException;
import com.jeonggolee.helpanimal.domain.user.query.UserSpecification;
import com.jeonggolee.helpanimal.domain.user.repository.UserRepository;
import com.jeonggolee.helpanimal.domain.user.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserSpecification userSpecification;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider provider;
    private final UserEmailService userEmailService;

    private String getAuthenticationName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private User getUser(String email) {
        return userRepository.findOne(userSpecification.searchWithEmailEqual(email))
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 회원입니다."));
    }

    public String signUpUser(UserSignupDto dto) {
        String encodePassword = passwordEncoder.encode(dto.getPassword());
        dto.PasswordEncoding(encodePassword);
        Optional<User> user = userRepository.findOne(userSpecification.searchWithEmailEqual(dto.getEmail()));
        if (user.isPresent()) {
            throw new UserDuplicationException("이미 존재하는 유저입니다.");
        }
        return userRepository.save(dto.toEntity()).getEmail();
    }

    public JwtTokenDto loginUser(UserLoginDto dto) {
        User user = getUser(dto.getEmail());
        boolean isMatchingPassword = passwordEncoder.matches(dto.getPassword(), user.getPassword());
        if (!isMatchingPassword) {
            throw new WrongPasswordException("잘못된 패스워드 입니다.");
        }
        return new JwtTokenDto(provider.generateToken(dto.getEmail(), Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString()))));
    }

    public UserInfoReadDto getUserInfo() {
        String email = getAuthenticationName();
        return new UserInfoReadDto(getUser(email));
    }

    public String sendEmail() {
        String email = getAuthenticationName();
        User user = getUser(email);
        String url = "/auth/email-verify/email?=" + email + "&authToken=" + UUID.randomUUID().toString();
        userEmailService.sendMail(email, "회원가입 인증 이메일입니다.", url);
        user.updateUrl(url);
        userRepository.save(user);
        return url;
    }

    public String authEmail(String url) {
        String email = getAuthenticationName();
        User user = getUser(email);
        if (!url.equals(user.getUrl())) {
            throw new WrongAuthenticationUrlException("잘못된 Url 입니다.");
        }
        user.updateRole(Role.USER);
        userRepository.save(user);
        return user.getRole().toString();
    }
}
