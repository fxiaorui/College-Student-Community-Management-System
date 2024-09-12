package com.example.college.student.community.management.system.pojo.param.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 新增用户参数类
 */
@Data
public class UserAddParam {

    /**
     * 学号
     */
    @NotNull(message = "请提交学号")
    private String studentId;

    /**
     * 密码(密文)
     */
    @NotNull(message = "请提交密码")
    private String password;

    /**
     * 姓名
     */
    @NotNull(message = "请提交姓名")
    private String name;

    /**
     * 性别
     */
    @NotNull(message = "请提交性别")
    private String sex;

    /**
     * 邮箱
     */
    @NotNull(message = "请提交邮箱")
    private String email;

    /**
     * 专业
     */
    @NotNull(message = "请提交专业")
    private String major;

    /**
     * 班级
     */
    @NotNull(message = "请提交班级")
    private String classId;

    /**
     * 角色列表
     */
    @NotEmpty(message = "请提交角色列表")
    private Long[] roleIds;
}
