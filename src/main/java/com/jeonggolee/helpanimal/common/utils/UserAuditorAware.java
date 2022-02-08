package com.jeonggolee.helpanimal.common.utils;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAuditorAware implements AuditorAware<String>{


    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //반환 부분 미숙한거 추후 테스트 코드와 함께 수정 예정
        return authentication == null ? Optional.of("anonymousUser") : Optional.of(authentication.getName());
    }
}
