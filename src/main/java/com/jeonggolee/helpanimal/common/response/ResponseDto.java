package com.jeonggolee.helpanimal.common.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseDto<T> {

    private boolean ok;
    private int status;
    private String message;
    private T data;
    private Error error;

    private ResponseDto(boolean ok, int status, String message, T data,
        Error error) {
        this.ok = ok;
        this.status = status;
        this.message = message;
        this.data = data;
        this.error = error;
    }

    public static ResponseDto ok() {
        return new ResponseDto(true, HttpStatus.OK.value(), null, null, null);
    }

    public static <T> ResponseDto<T> ok(T data) {
        return new ResponseDto<>(true, HttpStatus.OK.value(), null, data, null);
    }

    public static ResponseDto fail(int status, String message) {
        return new ResponseDto(false,
            status,
            null,
            null,
            Error.builder()
                .message(message)
                .build());
    }

    @Getter
    private static class Error {

        private String message;
        
        @Builder
        private Error(String message) {
            this.message = message;
        }
    }
}
