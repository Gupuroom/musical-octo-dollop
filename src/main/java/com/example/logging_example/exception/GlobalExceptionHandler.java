package com.example.logging_example.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CommonErrorResponse> handleCustomException(BusinessException exception) {
        exception.printStackTrace();

        CommonErrorCode errorCode = exception.getErrorCode();
        CommonErrorResponse errorResponse = CommonErrorResponse.of(errorCode);
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
