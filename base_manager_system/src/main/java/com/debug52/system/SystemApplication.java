package com.debug52.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

/**
 * @author debug52
 * @date 2022年04月24日 15:29
 */


@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.debug52.system.mapper")
@Import(value = com.debug52.utils.util.RedisUtil.class)
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
