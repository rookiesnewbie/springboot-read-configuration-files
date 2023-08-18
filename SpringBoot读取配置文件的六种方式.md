

## 获取Spring默认的全局配置文件

### 方式一：

使用@Value注解实现

application.yml配置文件

```yml
server:
  port: 8081

person:
  name: "李四"
  age: 18
  message: 666
```

实体类

```java
@Data
public class UserInfo {

    private String name;

    private Integer age;

    private String message;

}
```

测试：

```java
@RestController
@Slf4j
public class TestController {
    @Value("${person.name")
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
```

效果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/c59b2c543e9242408a4eb98b85a2307c.png#pic_center)





**注意：**

- **此方式有不足之处就是@Value("${person.name")中的person.name在配置文件application.yml中必须存在，若不存在则回报如下的错误：**

  -![在这里插入图片描述](https://img-blog.csdnimg.cn/0435161175474b1e91ddba884160bbd0.png#pic_center)


- **解决此项错误：在配置文件添加对应的属性或在@Value注解中使用默认值，@Value注解使用默认值方式如下**

    - **@Value("${person.name:")：表示配置文件没有name属性时，name的默认值为空**

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/65ac2e88afb64858b7721eb66a923f6a.png#pic_center)

    - **@Value("${person.name:张三")：表示配置文件没有name属性时，name的默认值为张三**

      ![在这里插入图片描述](https://img-blog.csdnimg.cn/6544798f5c17426eb5052a53ce18bac8.png#pic_center)


    - **@Value中使用默认值的前提时配置文件没有对应的属性，否则默认值会被配置文件的覆盖**

    ![在这里插入图片描述](https://img-blog.csdnimg.cn/269e9af09e10477396db5acc26a34c85.png#pic_center)




**==此方只适合读取配置文件属性较少时使用==**

### 方式二：

使用@ConfigurationProperties注解

application.yml配置文件

```yml
server:
  port: 8082

person:
  name: "李四"
  age: 19
  message: 777
```

用户实体类

```java
@Data
public class UserInfo {

    private String name;

    private Integer age;

    private String message;

}
```

获取配置文件信息配置类

```java
@Data
@Configuration
@ConfigurationProperties(prefix = "person")  //perfix绑定配置文件的前缀
public class AppConfig {

    private String name;
    private Integer age;
    private String message;

}
```



测试：

```java
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
```



效果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/f32d389b66c64164a5108680ab7550d7.png#pic_center)


**此方法适用于获取配置文件属性较多的情况下使用，==具体看个人喜好==**

### 方式三：

**使用Environment来获取，Environment是有Spring底层提供的Api，也是一个bean**

application.yml配置文件

```yml
server:
  port: 8083

person:
  name: "王五"
  age: 20
  message: 888
```

用户实体类：

```java
@Data
public class UserInfo {

    private String name;

    private Integer age;

    private String message;

}
```

测试：

```java
@RestController
public class TestController{

    @Resource
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
}
```

或：

```java
@RestController
public class TestController implements EnvironmentAware {

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
```

效果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/2ed2040b16fd4594b84401ced76bf42a.png#pic_center)


**此方法适用于动态获取配置文件信息的情况下使用**



**以上三种方式是获取Spring默认的全局配置文件**





## 获取外部配置文件

### 方式一：获取自定义的properties文件

**结合`@PropertySources`、`@PropertySource`和`@Value`三个注解实现**

**自定义的properties文件：custom.properties**

```properties
person.name = "赵六"
person.age = 21
person.message = "888"
```



配置读取自定义配置文件的类：

```java
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
```

用户实体类：

```java
@Data
public class UserInfo {

    private String name;

    private Integer age;

    private String message;

}
```



测试：

```java
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
```



效果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/e3b978926312474abe3255dbcf301031.png#pic_center)

### 方式二：获取自定义的yml文件

自定义yaml文件：custom.yml

```yml
person:
  name: "小六子"
  age: 22
  message: 555
```

yml配置文件

```yml
server:
  port: 8085
```



==**注意：在Spring默认的全局配置文件中，不能含有自定义文件中存在的K-V键值对，否则当结合@Value注解取值是优先取Spring默认全局配置文件中的值（即自定义配置文件不生效）**==



若yml配置文件如下，则优先显示Spring默认全局配置文件的内容

```
server:
  port: 8085
person:
  name: "zhaoliu"
  age: 22
  message: 666
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/25d09fafb4634202bf87cafcd969db87.png#pic_center)






配置读取自定义yml文件的配置类

```java
@Configuration
public class CustomYamlConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer yamlConfigure(){
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(new ClassPathResource("custom.yml"));
        configurer.setProperties(Objects.requireNonNull(yamlPropertiesFactoryBean.getObject()));
        return configurer;
    }

}
```

结合@Value注解实现

测试：

```java
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
```



效果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/c5a7ff9a28614a0e9deec84e383fc05a.png#pic_center)


### 方式三：Java原生态获取properties配置文件

**自定义的properties文件：custom.properties**

```properties
person.name = "六六六"
person.age = 22
person.message = "999"
```

yaml

```yml
server:
  port: 8086
```



```java
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
```

效果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/395853c06c5d414995a93625c3e039d1.png#pic_center)
