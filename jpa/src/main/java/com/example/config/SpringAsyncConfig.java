package com.example.config;

import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class SpringAsyncConfig {

    @Bean(name = "threadPoolTaskExecutor")  
    public TaskExecutor threadPoolTaskExecutor()   {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // Số lượng Thread mặc định trong Pool
        executor.setMaxPoolSize(15); // Số lượng tối đa Thread trong Pool
        executor.setQueueCapacity(25); // Số lượng tối da của BlockingQueue
        executor.setThreadNamePrefix("do-something-");
        return executor;

    } 

}
