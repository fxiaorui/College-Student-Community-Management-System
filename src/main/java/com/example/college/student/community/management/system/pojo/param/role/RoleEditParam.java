package com.example.college.student.community.management.system.pojo.param.role;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 编辑角色参数
 */
@Data
public class RoleEditParam implements Serializable {

    /**
     * 角色ID
     */
    @NotNull(message = "请提交角色ID")
    private Long id;

    /**
     * 角色名
     */
    @NotNull(message = "请提交角色名")
    private String name;

    /**
     * 序号
     */
    @NotNull(message = "请提交序号")
    private Integer sort;

    /**
     * 备注
     */
    private String comment;

    /**
     * 权限列表
     */
    @NotEmpty(message = "请提交权限列表")
    private Long[] permissionIds;

}
