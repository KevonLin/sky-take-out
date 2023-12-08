package com.sky.config;

import com.sky.properties.MinIoProperties;
import com.sky.utils.MinIoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kevonlin
 * @version 1.0.0
 * @date 2023/12/8 21:55
 * @description 创建MinIoUtil对象的配置类
 **/
@Configuration
@Slf4j
public class MinIoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public MinIoUtil getMinIoUtil(MinIoProperties minIoProperties) {
        log.info("创建minio上传工具类对象");
        return new MinIoUtil(minIoProperties.getEndpoint(),
                minIoProperties.getAccessKey(),
                minIoProperties.getSecretKey(),
                minIoProperties.getBucketName());
    }
}
