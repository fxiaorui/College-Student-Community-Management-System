package com.example.college.student.community.management.system.pojo.param.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 用户注册参数
 */
@Data
public class UserRegParam implements Serializable {

    /**
     * 学号
     */
    @NotNull(message = "请提交学号")
    private String studentId;

    /**
     * 密码(明文)
     */
    @NotNull(message = "请提交密码")
    @Pattern(regexp = "^.{4,15}$", message = "密码必须是4~15长度的字符组成")
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
}
