package com.example.college.student.community.management.system.dao.persist.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.college.student.community.management.system.pojo.entity.RolePermission;

import java.util.List;

/**
 * 角色-权限关联数据(持久)连接
 */
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    /**
     * 批量插入角色-权限关联数据
     *
     * @param rolePermissionList 角色-权限关联数据列表
     * @return 影响的数据行数
     */
    int insertBatch(List<RolePermission> rolePermissionList);
}
