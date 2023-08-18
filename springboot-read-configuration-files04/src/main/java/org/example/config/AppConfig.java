package org.example.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

/**
 * @Author LiTeng
 * @Date 2023/8/18 22:06
 * Version 1.0
 * @Description 获取自定义的properties配置文件
 */

@Data
@Component
@PropertySources({@PropertySource(value = "classpath:custom.properties",encoding = "UTF-8")})
public class AppConfig {
    @Value("${person.name:李四}")
    private String name;

    @Value("${person.age}")
    private Integer age;

    @Value("${person.message}")
    private String message;
}
