package com.example.college.student.community.management.system.pojo.vo.user;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户登录数据库查询返回信息
 */
@Data
public class UserLoginInfoVO implements Serializable {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 学号
     */
    private String studentId;

    /**
     * 密码(密文)
     */
    private String password;

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
     * 权限列表
     */
    private List<String> permissions;
}
