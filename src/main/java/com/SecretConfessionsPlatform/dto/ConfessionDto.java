package com.SecretConfessionsPlatform.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class ConfessionDto {
    private Long id;

    private String content;

    private LocalDateTime createdAt;
}
