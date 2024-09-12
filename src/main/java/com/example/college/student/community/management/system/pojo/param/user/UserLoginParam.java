package com.example.college.student.community.management.system.pojo.param.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 用户登录参数
 */
@Data
public class UserLoginParam implements Serializable {

    /**
     * 学号
     */
    @NotNull(message = "请提交学号")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,15}$", message = "用户名必须是4~15长度的字符组成，且不允许使用标点符号")
    private String studentId;

    /**
     * 密码(明文)
     */
    @NotNull(message = "请提交密码")
    @Pattern(regexp = "^.{4,15}$", message = "密码必须是4~15长度的字符组成")
    private String password;
}
