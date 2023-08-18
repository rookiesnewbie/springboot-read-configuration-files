package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;


/**
 * @Author LiTeng
 * @Date 2023/8/18 22:39
 * Version 1.0
 * @Description
 */

@RestController
@Slf4j
public class TestController {

    @GetMapping("/test")
    public Map<String,Object> get() {
        Properties properties = new Properties();

        try{
            InputStreamReader inputStreamReader = new InputStreamReader(
                    this.getClass().getClassLoader().getResourceAsStream("custom.properties"), UTF_8);

                    properties.load(inputStreamReader);

        }catch (IOException e){
            log.info("e={}",e);
        }

        log.info("Properties Name:{}",properties.getProperty("person.name"));
        log.info("Properties Age:{}",properties.getProperty("person.age"));
        log.info("Properties Message:{}",properties.getProperty("person.message"));

        HashMap<String, Object> map = new HashMap<>();
        map.put("name",properties.getProperty("person.name"));
        map.put("age",properties.getProperty("person.age"));
        map.put("message",properties.getProperty("person.message"));

        return map;
    }
}
