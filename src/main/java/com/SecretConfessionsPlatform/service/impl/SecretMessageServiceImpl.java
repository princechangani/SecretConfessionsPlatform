package com.SecretConfessionsPlatform.service.impl;

import com.SecretConfessionsPlatform.dto.SecretMessageDto;
import com.SecretConfessionsPlatform.entity.SecretMessage;
import com.SecretConfessionsPlatform.exception.ResourceNotFoundException;
import com.SecretConfessionsPlatform.mapper.SecretMessageMapper;
import com.SecretConfessionsPlatform.repository.SecretMessageRepository;
import com.SecretConfessionsPlatform.service.SecretMessageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@Service
public class SecretMessageServiceImpl implements SecretMessageService {
    private final SecretMessageRepository secretMessageRepository;

    private final RedisTemplate<String, String> redisTemplate;

    public SecretMessageServiceImpl(SecretMessageRepository secretMessageRepository, RedisTemplate<String, String> redisTemplate) {
        this.secretMessageRepository = secretMessageRepository;
        this.redisTemplate = redisTemplate;
    }


    @Override
    public String createSecretMessage(SecretMessageDto secretMessageDto, HttpServletRequest request) {

        String uniqueId = UUID.randomUUID().toString().trim();

        System.out.println("Received content: " + secretMessageDto.getContent());
        System.out.println("Received is for 24 hours: " + secretMessageDto.getIsForDay());

        secretMessageDto.setId(uniqueId);
        secretMessageDto.setExpiryTime(LocalDateTime.now().plusHours(24));

        SecretMessage message = SecretMessageMapper.toEntity(secretMessageDto);


        System.out.println("Received is for 24 hours: " + message.getIsForDay());

        secretMessageRepository.save(message);

        if (message.getIsForDay()) {
            String key = "secret:" + uniqueId;
            redisTemplate.opsForValue().set(key, message.getContent(), Duration.ofMinutes(20));
            System.out.println("Message stored in Redis: " + key);
        } else {
            System.out.println("Message not stored in Redis as isFor24h is false.");
        }

        return uniqueId;
    }

   @Override
//    @Cacheable(value = "secretMessages", key = "#id")
    public String readSecretMessage(String id , HttpServletRequest request) {



        Optional<SecretMessage> optionalMessage = secretMessageRepository.findById(id);

        if (optionalMessage.isPresent()) {
            SecretMessage message = optionalMessage.get();
            String ip = request.getRemoteAddr(); // Get client IP address

            if(message.getIsForDay()){
                String redisKey = "secret:" + id;
                String redisKey1 = "viewed:" + id;
                System.out.println("Redis key: " + redisKey);

                String messageContent = redisTemplate.opsForValue().get(redisKey);
                System.out.println("Message content from Redis: " + messageContent);

                Set<String> ips = redisTemplate.opsForSet().members(redisKey1);
                System.out.println("IPs: " + ips);


                if (ips.contains(ip)) {
                    throw new ResourceNotFoundException( "(redis) Message already viewed from this IP");
                }
                if (messageContent == null) {
                    throw new ResourceNotFoundException( "Message is expired");
                }


                redisTemplate.opsForSet().add(redisKey1, ip);

                return messageContent;


            }

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



}

