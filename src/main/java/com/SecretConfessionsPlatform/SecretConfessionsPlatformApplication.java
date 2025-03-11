package com.SecretConfessionsPlatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching

public class SecretConfessionsPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecretConfessionsPlatformApplication.class, args);
    }

}
