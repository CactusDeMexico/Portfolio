package com.pancarte.architecte;

import com.pancarte.architecte.model.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@SpringBootConfiguration
@EnableScheduling
public class ArchitecteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArchitecteApplication.class, args);
        Log log = new Log();
        log.test("done");
    }
}
