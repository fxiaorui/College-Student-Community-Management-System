package com.example.college.student.community.management.system.pojo.param.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 新增用户参数类
 */
@Data
public class UserAddParam {

    /**
     * 用户名
     */
    @NotNull(message = "请提交用户名")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,15}$", message = "用户名必须是4~15长度的字符组成，且不允许使用标点符号")
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
