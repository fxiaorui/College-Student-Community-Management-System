package com.example.college.student.community.management.system.dao.persist.repository;

import com.example.college.student.community.management.system.pojo.entity.RolePermission;

import java.util.List;

/**
 * 角色-权限关联数据(持久)仓库
 */
public interface RolePermissionRepository {

    /**
     * 批量插入角色-权限关联数据
     *
     * @param rolePermissionList 角色-权限关联数据列表
     * @return 影响的数据行数
     */
    int insertBatch(List<RolePermission> rolePermissionList);

    /**
     * 根据角色ID删除所有角色-权限关联数据
     * @param roleId 角色ID
     * @return 影响的数据行数
     */
    int deleteByRoleId(Long roleId);

    /**
     * 根据角色ID查询角色-权限关联数据
     * @param id 角色ID
     * @return 角色-权限关联数据
     */
    List<RolePermission> selectByRoleId(Long id);
}
