package org.example.controller;

import org.example.config.AppConfig;
import org.example.domain.UserInfo;
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

   @Resource
    private AppConfig appConfig;

   @GetMapping("/test")
    public UserInfo getUserInfo(){
       UserInfo userInfo = new UserInfo();

       userInfo.setName(appConfig.getName());
       userInfo.setAge(appConfig.getAge());
       userInfo.setMessage(appConfig.getMessage());

       return userInfo;
   }

}
