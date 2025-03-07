package com.SecretConfessionsPlatform.service;

import com.SecretConfessionsPlatform.dto.SecretMessageDto;
import jakarta.servlet.http.HttpServletRequest;

public interface SecretMessageService {
    public String createSecretMessage(SecretMessageDto secretMessageDto ,HttpServletRequest request);
    public String readSecretMessage(String id, HttpServletRequest request);
    public String readSecretMessage1(String id, HttpServletRequest request);
}
