# Easy-Auth
简单方便的认证、鉴权框架。 目前仅支持在SpringCloud分布式服务中使用。 

## 使用手册
### 网关模块
网关模块只需添加依赖和配置。框架会对token解析并放入header。
#### 依赖
```groovy
repositories {
    maven { url "https://jitpack.io" }
}

dependencies{
    implementation 'com.github.niceSong.EasyAuth:easy-auth-certification:v1.0.0'
}
```
#### 配置
```yaml
spring:
  redis:
    host: ${REDIS_ADDR: 192.168.68.94}
    database: 1
    port: ${REDIS_PORT:21202}
    timeout: 200000
    password: ${REDIS_PASSWORD:}

easy.auth:
  jwt:
    secret: auth2020
    issuer: auth.org
    headerTokenKey: token
    expire: 604800
```

### 认证/鉴权模块
核心模块：定义角色权限、认证鉴权。
#### 依赖：
```groovy
repositories {
    maven { url "https://jitpack.io" }
}

dependencies{
    implementation 'com.github.niceSong.EasyAuth:easy-auth-authentication:v1.0.0'
}
```
#### 配置
```yaml
easy.auth:
  jwt:
    secret: auth2020
    issuer: auth.org
    expire: 604800
```
#### 添加注解
* 添加`@EasyAuthRoleScan`注解
```java
@SpringBootApplication
@EasyAuthRoleScan
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
```
#### 定义角色及权限
```java
// 定义角色：`admin`，它拥有两个权限：“更改用户余额”、“查看网站统计”
@EasyAuthRole(permissions = {"changeUserBalance","viewWebStatistics"})
public class Admin {
}
```
#### 认证、鉴权
在需要认证或鉴权的请求接口中调用`auth()`接口。其传入的参数如下：
1. 该接口允许的权限。
2. request。
3. 用户自定义的操作，传入lambda即可，没有传入null。
```java
@Autowired
private EasyAuth easyAuth;
@Autowired
private HttpServletRequest request;

public void sample(){
    easyAuth.auth("changeUserBalance", request, null);
    System.out.println("认证/鉴权，成功");
}
```
#### 生成token
下面代码中，`UserBase`为框架自带数据结构。
```java
@Autowired
private EasyAuthJwtTools easyAuthJwtTools;

public String loginSample(){
    UserBase user = new UserBase(1", "Admin");
    return easyAuthJwtTools.generateToken(user,1L, TimeUnit.HOURS);
}
```

## 示例
示例详见模块：
* `easy-auth-gateway-sample` 
* `easy-auth-web-sample`