package com.debug52.gateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
@MapperScan("com.debug52.gateway.mapper")
@Import(value = com.debug52.utils.util.RedisUtil.class)
public class GatewayProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayProviderApplication.class, args);
    }

}
