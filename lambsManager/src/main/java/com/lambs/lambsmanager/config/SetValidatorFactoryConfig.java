package com.lambs.lambsmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * 配置数据校验返回模式(快速返回模式)
 */
@Configuration
public class SetValidatorFactoryConfig {
    /**
     * 为数据校验配置快速返回模式，遇错误直接返回，不再校验
     *
     * @return
     */
    @Bean
    public LocalValidatorFactoryBean getValidatorFactory() {
        /*实例化对象*/
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        /*设置模式*/
        localValidatorFactoryBean.getValidationPropertyMap().put("hibernate.validator.fail_fast", "true");
        /*返回模式*/
        return localValidatorFactoryBean;
    }
}
