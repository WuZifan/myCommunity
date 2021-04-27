# 项目

## 资料
[Spring](https://spring.io/guides)

[BootStrap](https://v3.bootcss.com/components/)

[接入Github登录](https://docs.github.com/en/developers/apps/creating-an-oauth-app)

[okhttp](https://square.github.io/okhttp/)

[fastjson](https://github.com/alibaba/fastjson)

[h2 database](https://www.h2database.com/html/main.html)

我这里在创建h2db的时候，必须要指定绝对路径

[mybatiss](https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)

[spring datasource](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#boot-features-configure-datasource)

[spring whitepage](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#boot-features-error-handling)


## 工具

[Visual Paradim](https://www.visual-paradigm.com)

[Maven Repo](http://mvnrepository.com/)

[flyway](https://flywaydb.org/documentation/getstarted/firststeps/maven)

```bash
mvn flyway:migrate
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate



```

##  笔记

### 笔记1：异常
异常不仅一种，某些业务异常能够被@ControllerAdvice处理，

而404异常springboot默认不抛出，所以没办法通过Controller来处理，

这个时候则需要通过继承exceptionController来处理

### 笔记2：并发
并发写的时候很容易出错，目前有这么几种方式避免并发写出错：

    1、一种是在update的java方法层面加锁
    2、一种是直接使用 updata table1 set a = a+1 where id=1 这样的写法
   
参考：
    
    https://blog.csdn.net/qq_41445224/article/details/89299758

### 笔记3：作用域

request：

    一个请求链，每一个请求都会创建一个request，作用域为当前的请求链，一般用于同一请求链之间不同页面的参数传递，如表单中的值传递。

session：

    Tomcat（服务器）会为每个会话创建一个session对象，作用域：session的数据只能在当前会话中的所有servlet共享（会话范围：如某个用户从首次访问服务器开始，到用户关闭浏览器结束），HttpSession底层依赖Cookie。

application（ServletContext）： 

    服务器会为每个web应用创建一个ServletContext对象，而ServletContext对象的创建时在服务器启动时完成的，在服务器关闭时销毁的， 作用：在整个web应用的动态资源之间共享数据。

### 笔记4：Model和session
Session：<b>session域</b>

    Session:在计算机中，尤其是在网络应用中，称为“会话”。它具体是指一个终端用户与交互系统进行通信的时间间隔，通常指从注册进入系统到注销退出系统之间所经过的时间。
    在网站使用中，为了保存用户信息。服务器会给每一个用户（浏览器）创建一个Session。
流程：

    1、客户端请求服务器
    2、服务器创建Session，在服务器端保存用户数据
    3、服务器返回给客户端一个SessionId（JSESESSION）是一个Cookie
    4、用户在Session作用时间内再次访问服务器就会根据SessionId取出用户的Session


Model：

    Model是一个接口
ModelMap是接口的实现。是将model中的数据填充到<b>request域</b>中，返回给客户端
总结：session数据保存在服务器，model数据放入视图中。session可以在不同页面使用。model只能在Controller返回的页面使用
