# Easy-Auth
简单方便的认证、鉴权框架。 目前仅支持SpringCloud分布式服务中使用。 
## 使用手册
### 网关模块
在网关，框架会对token解析，并将解析信息放入header。
在网关，框架没有进行认证，非法的token框架也会放行，因为认证应该在接口内进行，由接口自己决定是否需要认证。
#### 依赖
```groovy
repositories {
    maven { url "https://jitpack.io" }
}

dependencies{
    implementation 'org.tyytogether:easy-auth-certification:1.0.0-SNAPSHOT'
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
#### 依赖：
```groovy
repositories {
    maven { url "https://jitpack.io" }
}

dependencies{
    implementation 'org.tyytogether:easy-auth-authentication:1.0.0-SNAPSHOT'
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
### 代码
* 添加框架扫描注解
```java
@SpringBootApplication
@EasyAuthRoleScan
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
```
* 定义角色及角色对应权限，比如：
定义`admin`角色，其含有“更改用户余额”、“查看网站统计”两个权限
```java
@EasyAuthRole(permissions = {"changeUserBalance","viewWebStatistics"})
public class Admin {
}
```
* 认证、鉴权
下面代码中，`auth()`接口传入的参数：
* 该接口允许的权限。
* request。
* 用户自定义的操作（比如对token中的某些信息进行存储），传入lambda即可。
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
* 生成token
下面代码中，`UserBase`为框架的数据结构，`generateToken()`接口需传入`UserBase`的子类，可以自定义更多的信息存入token中。
```java
@Autowired
private EasyAuthJwtTools easyAuthJwtTools;

public String loginSample(){
    UserBase user = new UserBase(1", "Admin");
    return easyAuthJwtTools.generateToken(user,1L, TimeUnit.HOURS);
}
```
## 示例
示例详见以下模块：
* `easy-auth-gateway-sample` 
* `easy-auth-web-sample`