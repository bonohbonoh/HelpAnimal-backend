package com.jeonggolee.helpanimal.domain.user.service;

import com.jeonggolee.helpanimal.common.jwt.JwtTokenProvider;
import com.jeonggolee.helpanimal.domain.user.dto.UserLoginDto;
import com.jeonggolee.helpanimal.domain.user.dto.UserSignupDto;
import com.jeonggolee.helpanimal.domain.user.entity.User;
import com.jeonggolee.helpanimal.domain.user.exception.login.EmailPasswordNullPointException;
import com.jeonggolee.helpanimal.domain.user.exception.login.WrongPasswordException;
import com.jeonggolee.helpanimal.domain.user.exception.signup.UserDuplicationException;
import com.jeonggolee.helpanimal.domain.user.exception.signup.UserInfoNotFoundException;
import com.jeonggolee.helpanimal.domain.user.query.UserSearchSpecification;
import com.jeonggolee.helpanimal.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
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
            throw new UserDuplicationException("이미 존재하는 유저입니다.");
        }
        String email = userRepository.save(dto.toEntity()).getEmail();
        if (email != null && !email.equals("")) {
            return true;
        }
        return false;
    }

    public boolean signUpUserRequiredCheck(UserSignupDto dto) throws Exception {
        if (dto.getEmail() == null || dto.getPassword() == null || dto.getNickname() == null || dto.getName() == null || dto.getProfileImag() == null) {
            throw new UserInfoNotFoundException("필수 기입 항목을 입력해주세요.");
        }
        return signUpUser(dto);
    }

    public String loginUser(UserLoginDto dto) throws Exception {
        Optional<User> user = userRepository.findOne(userSearchSpecification.searchWithEmailEqual(dto.getEmail()));
        if (user.isPresent()) {
            boolean isMatchingPassword = passwordEncoder.matches(dto.getPassword(), user.get().getPassword());
            boolean isMatchingEmail = user.get().getEmail().matches(dto.getEmail());
            if (isMatchingPassword && isMatchingEmail) {
                return provider.generateToken(dto.getEmail(), Collections.singleton(new SimpleGrantedAuthority(user.get().getRole().toString())));
            }
        }
        throw new WrongPasswordException("잘못된 이메일 혹은 패스워드 입니다.");
    }

    public String logInUserRequiredCheck(UserLoginDto dto) throws Exception {
        if (dto.getEmail().equals("") || dto.getPassword().equals(""))
            throw new EmailPasswordNullPointException("이메일 혹은 패스워드를 입력해주세요.");
        return loginUser(dto);
    }
}
