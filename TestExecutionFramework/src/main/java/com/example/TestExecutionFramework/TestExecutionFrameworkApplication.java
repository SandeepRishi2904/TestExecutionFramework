package com.example.TestExecutionFramework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TestExecutionFrameworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestExecutionFrameworkApplication.class, args);
    }
}