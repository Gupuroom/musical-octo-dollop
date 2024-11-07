package com.example.logging_example.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TestErrorCode implements CommonErrorCode  {
    GET_TEST_ERROR("301","GET_TEST_ERROR"),
    POST_TEST_ERROR("302","POST_TEST_ERROR"),
    DELETE_TEST_ERROR("303","DELETE_TEST_ERROR");

    private final String code;
    private final String message;
}