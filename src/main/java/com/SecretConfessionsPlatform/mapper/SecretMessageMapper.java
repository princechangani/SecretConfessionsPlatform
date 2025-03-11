package com.SecretConfessionsPlatform.mapper;

import com.SecretConfessionsPlatform.dto.SecretMessageDto;
import com.SecretConfessionsPlatform.entity.SecretMessage;

public class SecretMessageMapper {


    public static SecretMessageDto toDto (SecretMessage secretMessage) {
        SecretMessageDto secretMessageDto = new SecretMessageDto();
        secretMessageDto.setId(secretMessage.getId());
        secretMessageDto.setContent(secretMessage.getContent());
        secretMessageDto.setExpiryTime(secretMessage.getExpiryTime());
        secretMessageDto.setViewed(secretMessage.isViewed());
        secretMessageDto.setIsForDay(secretMessage.getIsForDay());
        return secretMessageDto;
    }


    public static SecretMessage toEntity (SecretMessageDto secretMessageDto) {
        SecretMessage secretMessage = new SecretMessage();
        secretMessage.setId(secretMessageDto.getId());
        secretMessage.setContent(secretMessageDto.getContent());
        secretMessage.setExpiryTime(secretMessageDto.getExpiryTime());
        secretMessage.setViewed(secretMessageDto.isViewed());
        secretMessage.setIsForDay(secretMessageDto.getIsForDay());
        return secretMessage;

    }



}
