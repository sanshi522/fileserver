package com.sanshi.fileserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration   //标注一个类是配置类，spring boot在扫到这个注解时自动加载这个类相关的功能，比如前面的文章中介绍的配置AOP和拦截器时加在类上的Configuration
@EnableJpaRepositories(basePackages={"com.sanshi.fileserver.repository"})
@ServletComponentScan//扫描 @Controller、@Service 注解；
@EntityScan("com.sanshi.fileserver.bean")//扫描 @Entity 注解；
@SpringBootApplication
public class FileserverApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileserverApplication.class, args);
    }
}
