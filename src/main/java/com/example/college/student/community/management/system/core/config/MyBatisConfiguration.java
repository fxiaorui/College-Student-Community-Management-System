package com.example.college.student.community.management.system.core.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis的配置类
 */
@Slf4j
@Configuration
@MapperScan({
        "com.example.college.student.community.management.system.dao.persist.mapper",
})
public class MyBatisConfiguration {
}
