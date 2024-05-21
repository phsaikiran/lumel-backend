package com.example.lumel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LumelApplication {

    public static void main(String[] args) {
        SpringApplication.run(LumelApplication.class, args);
    }

}
