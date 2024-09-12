package com.example.college.student.community.management.system.pojo.vo.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户登录返回VO类
 */
@Data
@Accessors(chain = true)
public class UserLoginResultVO implements Serializable {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 学号
     */
    private String studentId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 专业
     */
    private String major;

    /**
     * 班级
     */
    private String classId;

    /**
     * 状态
     */
    private String status;

    /**
     * Token（JWT）
     */
    private String token;
}
