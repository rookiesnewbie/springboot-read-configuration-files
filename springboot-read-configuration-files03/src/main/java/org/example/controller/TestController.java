package org.example.controller;

import org.example.domain.UserInfo;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
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
public class TestController implements EnvironmentAware {

//    @Resource
    private Environment environment;

    @GetMapping("/test")
    public UserInfo getUserInfo(){
        UserInfo userInfo = new UserInfo();
        String name = environment.getProperty("person.name");

        Integer age = Integer.valueOf(environment.getProperty("person.age"));
        String message = environment.getProperty("person.message");
        userInfo.setName(name);
        userInfo.setAge(age);
        userInfo.setMessage(message);
        return userInfo;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
