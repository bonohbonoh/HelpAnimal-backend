package com.jeonggolee.helpanimal.domain.user.util;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", 0),
    USER("ROLE_USER", 1),
    ADMIN("ROLE_ADMIN", 2);

    private final String key;
    private final Integer title;
}