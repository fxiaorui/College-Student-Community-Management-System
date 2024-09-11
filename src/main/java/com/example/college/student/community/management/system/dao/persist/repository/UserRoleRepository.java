package com.example.college.student.community.management.system.dao.persist.repository;


import com.example.college.student.community.management.system.pojo.entity.UserRole;

import java.util.List;

/**
 * 用户-角色关联数据(持久)仓库
 */
public interface UserRoleRepository {

    /**
     * 插入一条用户-角色关联数据
     *
     * @param userRole 用户-角色关联数据
     * @return 影响的数据行数
     */
    int insert(UserRole userRole);

    /**
     * 批量插入用户-角色关联数据
     *
     * @param userRoleList 用户-角色关联数据列表
     * @return 影响的数据行数
     */
    int insertBatch(List<UserRole> userRoleList);

    /**
     * 根据用户ID删除用户-角色关联数据
     * @param id 用户ID
     * @return 影响的数据行数
     */
    int deleteByUserId(Long id);

    /**
     * 根据用户ID查询用户-角色关联数据
     * @param id 用户ID
     * @return 用户-角色关联数据
     */
    List<UserRole> selectByUserId(Long id);

    /**
     * 根据角色ID查询用户-角色关联数据
     * @param id 角色ID
     * @return 用户-角色关联数据
     */
    List<UserRole> selectByRoleId(Long id);
}
