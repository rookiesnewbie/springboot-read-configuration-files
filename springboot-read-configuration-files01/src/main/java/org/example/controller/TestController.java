package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author LiTeng
 * @Date 2023/8/18 18:14
 * Version 1.0
 * @Description SpringBoot读取配置文件信息测试
 */

@RestController
@Slf4j
public class TestController {
    @Value("${person.name:李四}")
    private String name;

    @Value("${person.age}")
    private Integer age;

    @Value("${person.message}")
    private String message;

    @GetMapping("/test")
    public UserInfo getUser(){
        UserInfo user = new UserInfo();
        user.setName(name);
        user.setAge(age);
        user.setMessage(message);
        log.info("name= {}",name);
        log.info("age= {} ",age);
        log.info("message= {}",message);
        return user;
    }

}
