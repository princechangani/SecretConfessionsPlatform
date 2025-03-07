package com.SecretConfessionsPlatform.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;



@Data
public class SecretMessageDto {

    private String id; // UUID


    private String content;


    private LocalDateTime expiryTime;

    private boolean viewed = false;
    private boolean isFor24h = false;
}
