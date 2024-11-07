package com.example.logging_example.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommonErrorResponse {
    private String code;
    private String message;
    private LocalDateTime timestamp;

    public static CommonErrorResponse of(CommonErrorCode errorCode) {
        return CommonErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
