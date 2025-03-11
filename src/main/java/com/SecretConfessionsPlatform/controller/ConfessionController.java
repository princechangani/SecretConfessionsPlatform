package com.SecretConfessionsPlatform.controller;


import com.SecretConfessionsPlatform.dto.ConfessionDto;
import com.SecretConfessionsPlatform.entity.Confession;
import com.SecretConfessionsPlatform.service.ConfessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/confessions")
public class ConfessionController {
    private final ConfessionService confessionService;

    public ConfessionController(ConfessionService confessionService) {
        this.confessionService = confessionService;
    }

    @PostMapping
    public ResponseEntity<ConfessionDto> createConfession(@RequestBody ConfessionDto confessionDto) {
        return ResponseEntity.ok(confessionService.saveConfession(confessionDto));
    }

    @GetMapping
    public ResponseEntity<List<ConfessionDto>> getConfessions() {
        return ResponseEntity.ok(confessionService.getAllConfessions());
    }
}
