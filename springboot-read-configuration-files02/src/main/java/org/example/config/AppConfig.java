package org.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author LiTeng
 * @Date 2023/8/18 19:05
 * Version 1.0
 * @Description SpringBoot读取配置文件
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "person")  //perfix绑定配置文件的前缀
public class AppConfig {

    private String name;
    private Integer age;
    private String message;

}
