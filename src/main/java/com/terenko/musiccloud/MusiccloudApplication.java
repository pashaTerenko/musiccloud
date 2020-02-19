package com.terenko.musiccloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
@EnableWebMvc
public class MusiccloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusiccloudApplication.class, args);
    }

}
