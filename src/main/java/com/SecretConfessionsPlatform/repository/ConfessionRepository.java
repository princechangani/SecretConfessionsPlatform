package com.SecretConfessionsPlatform.repository;

import com.SecretConfessionsPlatform.entity.Confession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfessionRepository extends JpaRepository<Confession, Long> {}

