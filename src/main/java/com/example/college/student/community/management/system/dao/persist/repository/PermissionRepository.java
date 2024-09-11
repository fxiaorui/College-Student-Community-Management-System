package com.example.college.student.community.management.system.dao.persist.repository;

import com.example.college.student.community.management.system.common.pojo.vo.PageData;
import com.example.college.student.community.management.system.pojo.entity.Permission;
import com.example.college.student.community.management.system.pojo.query.permission.PermissionQuery;

import java.util.List;

/**
 * 权限数据(持久)仓库
 */
public interface PermissionRepository {

    /**
     * 查询权限列表(所有数据)
     *
     * @param permissionQuery 查询参数
     * @return 权限列表
     */
    List<Permission> list(PermissionQuery permissionQuery);

    /**
     * 查询权限列表
     *
     * @param permissionQuery 查询参数
     * @param pageNum         页码
     * @param pageSize        每页记录数
     * @return 权限列表
     */
    PageData<Permission> list(PermissionQuery permissionQuery, Integer pageNum, Integer pageSize);
}
