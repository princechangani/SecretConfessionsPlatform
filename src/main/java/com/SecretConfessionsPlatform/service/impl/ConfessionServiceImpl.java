package com.SecretConfessionsPlatform.service.impl;

import com.SecretConfessionsPlatform.dto.ConfessionDto;
import com.SecretConfessionsPlatform.entity.Confession;
import com.SecretConfessionsPlatform.mapper.ConfessionMapper;
import com.SecretConfessionsPlatform.repository.ConfessionRepository;
import com.SecretConfessionsPlatform.service.ConfessionService;
import org.apache.el.stream.Stream;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;



@Service
public class ConfessionServiceImpl implements ConfessionService {

    private final ConfessionRepository confessionRepository;

    public ConfessionServiceImpl(ConfessionRepository confessionRepository) {
        this.confessionRepository = confessionRepository;
    }


    @Override
    public ConfessionDto saveConfession(ConfessionDto confessionDto) {

        confessionDto.setCreatedAt(LocalDateTime.now());
        Confession confession = ConfessionMapper.toEntity(confessionDto);
        Confession savedConfession = confessionRepository.save(confession);

        return ConfessionMapper.toDto(savedConfession);
    }

    @Override
    public List<ConfessionDto> getAllConfessions() {
        List<Confession> confessions = confessionRepository.findAll();
        List<ConfessionDto> confessionDtos = confessions.stream().map(ConfessionMapper::toDto).toList();


        return confessionDtos;
    }
}
