package com.example.college.student.community.management.system.pojo.param.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 修改密码参数类
 */
@Data
public class RePasswordParam implements Serializable {

    /**
     * 用户ID
     */
    @NotNull(message = "请提交用户ID")
    private Long userId;

    /**
     * 旧密码(明文)
     */
    @NotNull(message = "请提交旧密码")
    @Pattern(regexp = "^.{4,15}$", message = "旧密码必须是4~15长度的字符组成")
    private String password;

    /**
     * 新密码(明文)
     */
    @NotNull(message = "请提交新密码")
    @Pattern(regexp = "^.{4,15}$", message = "新密码必须是4~15长度的字符组成")
    private String newPassword;

}
