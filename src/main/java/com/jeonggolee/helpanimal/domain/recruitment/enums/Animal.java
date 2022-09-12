package com.jeonggolee.helpanimal.domain.recruitment.enums;

public enum Animal {
    DOG("DOG", "개"),
    CAT("CAT", "고양이");

    private String code;
    private String description;

    Animal(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
