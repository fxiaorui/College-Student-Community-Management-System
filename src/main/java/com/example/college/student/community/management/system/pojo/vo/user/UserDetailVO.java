package com.example.college.student.community.management.system.pojo.vo.user;

import lombok.Data;

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
     * 科室ID
     */
    private Long deptId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private String sex;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 角色列表
     */
    private Long[] roleIds;
}
