package com.debug52.system.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
@Slf4j
@Profile({"dev","uat"})
public class SwaggerConfig {
    @Value("${swagger.termsOfServiceUrl}")
    private String termsOfServiceUrl;
    @Value("${swagger.version}")
    private String version;
    @Value("${swagger.basePackage}")
    private String basePackage;
    @Bean
    public Docket createRestApi() {
        log.info("###########",basePackage,termsOfServiceUrl,version);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //是否开启 (true 开启  false隐藏。生产环境建议隐藏)
                //.enable(false)
                .select()
                //扫描的路径包,设置basePackage会将包下的所有被@Api标记类的所有方法作为api
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                //指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //设置文档标题(API名称)
                .title("种子中台接口")
                //文档描述
                .description("接口说明")
                //服务条款URL
                .termsOfServiceUrl(termsOfServiceUrl)
                //版本号
                .version(version)
                .build();
    }
}
