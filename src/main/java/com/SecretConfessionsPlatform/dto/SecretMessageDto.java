package com.SecretConfessionsPlatform.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;



@Data
public class SecretMessageDto {

    private String id; // UUID


    private String content;


    private LocalDateTime expiryTime;

    private boolean viewed ;

    private Boolean isForDay ;
}
