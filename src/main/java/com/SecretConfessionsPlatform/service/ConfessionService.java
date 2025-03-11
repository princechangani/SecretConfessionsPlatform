package com.SecretConfessionsPlatform.service;

import com.SecretConfessionsPlatform.dto.ConfessionDto;

import java.util.List;

public interface ConfessionService {
    public ConfessionDto saveConfession(ConfessionDto confessionDto);
    public List<ConfessionDto> getAllConfessions();
}
