package com.example.logging_example.controller;

import com.example.logging_example.controller.response.ResponseTestRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/response/logging")
public class ResponseLoggingController {

    @GetMapping
    public ResponseEntity<?> testResponseBodyByGet() {
        ResponseTestRecord responseTestRecord = new ResponseTestRecord("1", "GET");
        return ResponseEntity.ok().body(responseTestRecord);
    }

    @PostMapping
    public ResponseEntity<?> testResponseBodyByPost() {
        ResponseTestRecord responseTestRecord = new ResponseTestRecord("1", "POST");
        return ResponseEntity.ok().body(responseTestRecord);
    }

    @DeleteMapping
    public ResponseEntity<?> testResponseBodyByDelete() {
        return ResponseEntity.ok().build();
    }
}