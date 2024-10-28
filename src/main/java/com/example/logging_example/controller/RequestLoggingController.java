package com.example.logging_example.controller;

import com.example.logging_example.controller.request.PostRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/request/logging")
public class RequestLoggingController {

    @GetMapping
    public ResponseEntity<?> getParamTest(@RequestParam String name) {
        return ResponseEntity.ok().body("getParamTest OK");
    }
    @PostMapping
    public ResponseEntity<?> postBodyTest(@RequestBody PostRecord record) {
        return ResponseEntity.ok().body("postBodyTest OK");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchPathVariableTest(@PathVariable String id) {
        return ResponseEntity.ok().body("patchPathVariableTest OK");
    }
}