package com.example.college.student.community.management.system.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 * 使用在Query查询类中的属性上
 * 用于指定数据库的字段名与标记是否进行模糊查询
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryName {

    /**
     * 数据库字段名
     *
     * @return 数据库字段名
     */
    String value();

    /**
     * 是否进行模糊查询
     *
     * @return 是否进行模糊查询
     */
    boolean isFuzzy() default false;
}
