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
     * 数据id
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
     * 密码（密文）
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 是否启用，1=启用，0=未启用
     */
    private Integer status;

    /**
     * 权限列表
     */
    private List<String> permissions;

}
