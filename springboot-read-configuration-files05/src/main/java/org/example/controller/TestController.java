package org.example.controller;

import org.example.config.CustomYamlConfig;
import org.example.domain.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author LiTeng
 * @Date 2023/8/18 18:14
 * Version 1.0
 * @Description SpringBoot读取配置文件信息测试
 */

@RestController
public class TestController {

    @Value("${person.name:李四}")
    private String name;

    @Value("${person.age}")
    private Integer age;

    @Value("${person.message}")
    private String message;

    @GetMapping("/test")
    public UserInfo getUserInfo(){
        UserInfo userInfo = new UserInfo();
        userInfo.setName(name);
        userInfo.setAge(age);
        userInfo.setMessage(message);
        return userInfo;
    }

}
