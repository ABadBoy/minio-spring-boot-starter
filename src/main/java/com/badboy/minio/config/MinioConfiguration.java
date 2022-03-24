package com.badboy.minio.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(MinioClient.class)
@ConditionalOnProperty(value = "spring.minio.url")
@EnableConfigurationProperties(MinIoProperties.class)
public class MinioConfiguration {

  @Autowired
  private MinIoProperties minIoProperties;

  @Bean
  public MinioClient minioClient() {
    MinioClient minioClient = MinioClient.builder()
        .endpoint(minIoProperties.getUrl())
        .credentials(minIoProperties.getAccessKey(), minIoProperties.getSecretKey())
        .build();
    return minioClient;
  }


}
