package com.example.college.student.community.management.system.pojo.vo.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户详情返回VO类
 */
@Data
public class UserDetailVO implements Serializable {

    /**
     * ID
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
     * 角色列表
     */
    private Long[] roleIds;
}
