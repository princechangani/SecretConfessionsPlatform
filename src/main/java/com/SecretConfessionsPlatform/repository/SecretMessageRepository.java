package com.SecretConfessionsPlatform.repository;

import com.SecretConfessionsPlatform.entity.SecretMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecretMessageRepository extends JpaRepository<SecretMessage, String> {}

