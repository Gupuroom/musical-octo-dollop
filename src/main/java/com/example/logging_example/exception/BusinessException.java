package com.example.logging_example.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final CommonErrorCode errorCode;

    public BusinessException(CommonErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}