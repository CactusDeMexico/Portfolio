package com.pancarte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableFeignClients("com.pancarte.proxy")
@EnableScheduling
public class LibrarySpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibrarySpringbootApplication.class, args);

    }
}
