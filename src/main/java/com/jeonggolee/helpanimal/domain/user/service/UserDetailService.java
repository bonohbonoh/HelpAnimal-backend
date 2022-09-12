package com.jeonggolee.helpanimal.domain.user.service;

import com.jeonggolee.helpanimal.domain.user.dto.CustomUserDetails;
import com.jeonggolee.helpanimal.domain.user.entity.UserEntity;
import com.jeonggolee.helpanimal.domain.user.query.UserSpecification;
import com.jeonggolee.helpanimal.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserSpecification userSpecification;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findOne(
                userSpecification.searchWithEmailEqual(email))
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new CustomUserDetails(userEntity);
    }

}
