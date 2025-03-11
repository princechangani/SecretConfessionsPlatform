package com.SecretConfessionsPlatform.mapper;

import com.SecretConfessionsPlatform.dto.ConfessionDto;
import com.SecretConfessionsPlatform.entity.Confession;

import java.util.List;

public class ConfessionMapper {

    public static ConfessionDto toDto (Confession confession) {
        ConfessionDto confessionDto = new ConfessionDto();
        confessionDto.setId(confession.getId());
        confessionDto.setContent(confession.getContent());
        confessionDto.setCreatedAt(confession.getCreatedAt());
        return confessionDto;
    }
    public static Confession toEntity (ConfessionDto confessionDto) {
        Confession confession = new Confession();
        confession.setContent(confessionDto.getContent());
        confession.setCreatedAt(confessionDto.getCreatedAt());
        return confession;
    }


}
