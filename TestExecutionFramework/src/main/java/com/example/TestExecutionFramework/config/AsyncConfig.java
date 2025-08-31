package com.example.TestExecutionFramework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(15); // Start with 15 threads
        executor.setMaxPoolSize(30); // Support up to 30 concurrent threads
        executor.setQueueCapacity(50); // Increase queue for bursts
        executor.setThreadNamePrefix("TestExecutor-");
        executor.initialize();
        return executor;
    }
}