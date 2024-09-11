package com.example.college.student.community.management.system.pojo.vo.role;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色详情返回VO类
 */
@Data
public class RoleDetailVO implements Serializable {

    /**
     * 角色ID
     */
    private Long id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 备注
     */
    private String comment;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 权限ID列表
     */
    private Long[] permissionIds;

}
