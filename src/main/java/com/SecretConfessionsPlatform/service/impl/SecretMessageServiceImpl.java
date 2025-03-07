package com.SecretConfessionsPlatform.service.impl;

import com.SecretConfessionsPlatform.dto.SecretMessageDto;
import com.SecretConfessionsPlatform.entity.SecretMessage;
import com.SecretConfessionsPlatform.exception.ResourceNotFoundException;
import com.SecretConfessionsPlatform.mapper.SecretMessageMapper;
import com.SecretConfessionsPlatform.repository.SecretMessageRepository;
import com.SecretConfessionsPlatform.service.SecretMessageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class SecretMessageServiceImpl implements SecretMessageService {
    private final SecretMessageRepository secretMessageRepository;

    private final RedisTemplate<String, String> redisTemplate;

    public SecretMessageServiceImpl(SecretMessageRepository secretMessageRepository, RedisTemplate<String, String> redisTemplate) {
        this.secretMessageRepository = secretMessageRepository;
        this.redisTemplate = redisTemplate;
    }



    public String createSecretMessage1(SecretMessageDto secretMessageDto, HttpServletRequest request) {
        String id = UUID.randomUUID().toString();
        secretMessageDto.setId(id);
        secretMessageDto.setExpiryTime(LocalDateTime.now().plusHours(24));

        SecretMessage message = SecretMessageMapper.toEntity(secretMessageDto);

        // Save message in DB
        secretMessageRepository.save(message);

        if (message.isFor24h()) {
            String redisKey = "message:" + id;

            redisTemplate.opsForValue().set(redisKey, message.getContent());

            redisTemplate.expire(redisKey, 2, TimeUnit.MINUTES);
        }

        return id;
    }

   @Override
    public String createSecretMessage(SecretMessageDto secretMessageDto ,HttpServletRequest request) {
        String id = UUID.randomUUID().toString().trim();
        SecretMessage message = new SecretMessage();
        secretMessageDto.setId(id);
        secretMessageDto.setExpiryTime(LocalDateTime.now().plusHours(24));
        message = SecretMessageMapper.toEntity(secretMessageDto);
        secretMessageRepository.save(message);
        if(message.isFor24h()){
            String redisKey = "viewed:" + id;
            redisTemplate.opsForSet().add(redisKey, request.getRemoteAddr());
            redisTemplate.expire(redisKey, 24, TimeUnit.HOURS);
        }
        return secretMessageDto.getId();

    }

    @Override
    public String readSecretMessage(String id , HttpServletRequest request) {
        Optional<SecretMessage> optionalMessage = secretMessageRepository.findById(id);

        if (optionalMessage.isPresent()) {
            SecretMessage message = optionalMessage.get();
            String ip = request.getRemoteAddr();  // Get client IP address


            if (message.getViewedIps().contains(ip)) {
                throw new ResourceNotFoundException( "Message already viewed from this IP");
            }

            message.addViewedIp(ip);

            message.setViewed(true);

            secretMessageRepository.save(message);  // Persist the update

            // Return the message content
            return message.getContent();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found");
    }




    @Override
    public String readSecretMessage1(String id, HttpServletRequest request) {
        Optional<SecretMessage> optionalMessage = secretMessageRepository.findById(id);

        if (optionalMessage.isPresent()) {
            SecretMessage message = optionalMessage.get();

            String ip = request.getRemoteAddr();
            String redisKey = "viewed:" + id;

            Set<String> viewedIps = redisTemplate.opsForSet().members(redisKey);
            if (viewedIps != null && viewedIps.contains(ip)) {
                throw new ResponseStatusException(HttpStatus.GONE, "Message already viewed from this IP");
            }

            redisTemplate.opsForSet().add(redisKey, ip);
            redisTemplate.expire(redisKey, 2, TimeUnit.MINUTES);

            return message.getContent();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found");
    }

}

