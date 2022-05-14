package com.example.socialnewsspring;

import com.example.socialnewsspring.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
@EnableWebMvc
public class SocialNewsSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialNewsSpringApplication.class, args);
    }

}
