package com.example.college.student.community.management.system.pojo.param.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 编辑用户参数
 */
@Data
public class UserEditParam {

    /**
     * 用户ID
     */
    @NotNull(message = "请提交用户ID")
    private Long id;

    /**
     * 用户名
     */
    @NotNull(message = "请提交用户名")
    private String username;

    /**
     * 昵称
     */
    @NotNull(message = "请提交昵称")
    private String nickname;

    /**
     * 性别
     */
    @NotNull(message = "请提交性别")
    private String sex;

    /**
     * 角色列表
     */
    @NotEmpty(message = "请提交角色列表")
    private Long[] roleIds;
}
