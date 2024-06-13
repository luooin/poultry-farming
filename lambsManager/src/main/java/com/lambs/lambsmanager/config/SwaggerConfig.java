package com.lambs.lambsmanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("标题")
                        //描叙
                        .description("我的API文档")
                        //版本
                        .version("v1")
                        //作者信息，自行设置
                        .contact(new Contact().name("彭").email("11111").url("https://www.baidu.com"))
                        //设置接口文档的许可证信息
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

}
