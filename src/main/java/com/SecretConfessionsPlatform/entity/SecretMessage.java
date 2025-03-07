package com.SecretConfessionsPlatform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "secret_messages")
public class SecretMessage {

    @Id
    private String id; // UUID

    private String content;

    private LocalDateTime expiryTime;

    private boolean viewed = false;

    @ElementCollection
    private Set<String> viewedIps = new HashSet<>();

    private boolean isFor24h = false;



    public void addViewedIp(String ip) {
        this.viewedIps.add(ip);
    }
}

