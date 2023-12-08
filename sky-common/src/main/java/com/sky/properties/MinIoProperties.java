package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author kevonlin
 * @version 1.0.0
 * @date 2023/12/8 21:40
 * @description
 **/
@Component
@ConfigurationProperties(prefix = "sky.minio")
@Data
public class MinIoProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;
}
