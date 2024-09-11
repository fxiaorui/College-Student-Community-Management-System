package com.example.college.student.community.management.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 权限实体列
 */
@Data
public class Permission implements Serializable {

    /**
     * 权限ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 权限名
     */
    private String name;

    /**
     * 权限值
     */
    private String value;
}
