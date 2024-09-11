package com.example.college.student.community.management.system.pojo.vo.permission;

import lombok.Data;

import java.io.Serializable;

/**
 * 权限列表项VO类
 */
@Data
public class PermissionListItemVO implements Serializable {

    /**
     * 权限ID
     */
    private Long id;

    /**
     * 权限名
     */
    private String name;

}
