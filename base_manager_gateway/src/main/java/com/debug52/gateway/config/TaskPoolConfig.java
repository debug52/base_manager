package com.debug52.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

//线程池的配置
@Configuration
@EnableAsync
public class TaskPoolConfig {
    @Bean("taskExecutor")
    public Executor taskExecutro(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //活跃数
        taskExecutor.setCorePoolSize(10);
        //最大线程数量
        taskExecutor.setMaxPoolSize(50);
        taskExecutor.setQueueCapacity(200);
        //无任务存活最长时间
        taskExecutor.setKeepAliveSeconds(60);
        //线程池前缀
        taskExecutor.setThreadNamePrefix("taskExecutor--");
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        //保持等待执行结束最长时间
        taskExecutor.setAwaitTerminationSeconds(60);
        return taskExecutor;
    }

}
