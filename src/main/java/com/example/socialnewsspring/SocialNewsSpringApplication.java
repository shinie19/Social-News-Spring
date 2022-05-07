package com.example.socialnewsspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SocialNewsSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialNewsSpringApplication.class, args);
    }

}
