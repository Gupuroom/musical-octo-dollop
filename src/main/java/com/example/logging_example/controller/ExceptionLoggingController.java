package com.example.logging_example.controller;

import com.example.logging_example.exception.BusinessException;
import com.example.logging_example.exception.TestErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exception/logging")
public class ExceptionLoggingController {

    @GetMapping
    public ResponseEntity<?> testExceptionByGet() {
        throw new BusinessException(TestErrorCode.GET_TEST_ERROR);
    }

    @PostMapping
    public ResponseEntity<?> testExceptionByPost() {
        throw new BusinessException(TestErrorCode.POST_TEST_ERROR);
    }

    @DeleteMapping
    public ResponseEntity<?> testExceptionByDelete() {
        throw new BusinessException(TestErrorCode.DELETE_TEST_ERROR);
    }
}