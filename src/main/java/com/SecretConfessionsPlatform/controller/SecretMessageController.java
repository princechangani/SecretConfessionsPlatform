package com.SecretConfessionsPlatform.controller;

import com.SecretConfessionsPlatform.dto.SecretMessageDto;
import com.SecretConfessionsPlatform.service.SecretMessageService;
import com.SecretConfessionsPlatform.service.impl.SecretMessageServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class SecretMessageController {

    private final SecretMessageService secretMessageService;
    private final SecretMessageServiceImpl secretMessageServiceImpl;

    public SecretMessageController(SecretMessageService secretMessageService, SecretMessageServiceImpl secretMessageServiceImpl) {
        this.secretMessageService = secretMessageService;
        this.secretMessageServiceImpl = secretMessageServiceImpl;
    }

    @PostMapping
    public ResponseEntity<String> createMessage(@RequestBody SecretMessageDto secretMessageDto,HttpServletRequest request) {
        return ResponseEntity.ok(secretMessageService.createSecretMessage(secretMessageDto ,request));
    }
    @PostMapping("/test")
    public ResponseEntity<String> createMessage1(@RequestBody SecretMessageDto secretMessageDto,HttpServletRequest request) {
        return ResponseEntity.ok(secretMessageServiceImpl.createSecretMessage1(secretMessageDto ,request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> readSecretMessage(@PathVariable String id, HttpServletRequest request) {
        String message = secretMessageService.readSecretMessage(id, request);
        return ResponseEntity.ok(message);
    }
}
