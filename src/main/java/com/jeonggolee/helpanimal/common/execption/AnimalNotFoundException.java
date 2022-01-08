package com.jeonggolee.helpanimal.common.execption;

public class AnimalNotFoundException extends RuntimeException{
    public AnimalNotFoundException(String message) {
        super(message);
    }
}
