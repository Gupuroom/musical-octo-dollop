package com.example.logging_example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logging")
public class LoggingController {

    @GetMapping
    public ResponseEntity<?> getTest() {
        return ResponseEntity.ok().body("Get Test OK");
    }

    @PostMapping
    public ResponseEntity<?> postTest() {
        return ResponseEntity.ok().body("Post Test OK");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTest() {
        return ResponseEntity.ok().body("Delete Test OK");
    }

    @PatchMapping
    public ResponseEntity<?> patchTest() {
        return ResponseEntity.ok().body("Patch Test OK");
    }
}