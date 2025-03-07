package com.SecretConfessionsPlatform.service;

import com.SecretConfessionsPlatform.dto.ConfessionDto;

import java.util.List;

public interface ConfessionService {
    public ConfessionDto saveConfession(String content);
    public List<ConfessionDto> getAllConfessions();
}
