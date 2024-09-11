package com.example.college.student.community.management.system.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC配置类
 */
@Slf4j
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  //添加映射路径，这里是指所有路径
                .allowedHeaders("*")  //设置允许的请求头，这里是允许所有请求头
                .allowedMethods("*")  //设置允许的请求方法，这里是允许所有请求方法
                .allowedOriginPatterns("*")  //设置允许的请求来源，这里是允许所有来源
                .allowCredentials(true)  //设置是否允许发送Cookie，这里设置为true表示允许
                .maxAge(3600);  //设置预检请求的缓存时间，单位为秒，这里设置为3600秒，即1小时
    }

}