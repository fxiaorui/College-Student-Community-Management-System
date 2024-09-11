package com.example.college.student.community.management.system.dao.persist.repository;


import com.example.college.student.community.management.system.common.pojo.vo.PageData;
import com.example.college.student.community.management.system.pojo.entity.Role;
import com.example.college.student.community.management.system.pojo.query.role.RoleQuery;

/**
 * 角色数据(持久)仓库
 */
public interface RoleRepository {

    /**
     * 插入一条角色数据
     *
     * @param role 角色数据
     * @return 影响的数据行数
     */
    int insert(Role role);

    /**
     * 根据ID删除角色
     *
     * @param id 角色ID
     * @return 影响的数据行数
     */
    int deleteById(Long id);

    /**
     * 根据ID修改角色数据
     *
     * @param role 修改的角色数据(包含角色ID)
     * @return 影响的数据行数
     */
    int updateById(Role role);

    /**
     * 根据ID查询角色详情
     *
     * @param id 角色ID
     * @return 角色详情数据
     */
    Role selectById(Long id);

    /**
     * 根据角色名查询角色数据
     *
     * @param name 角色名
     * @return 角色数据
     */
    Role selectByName(String name);

    /**
     * 根据角色名查询ID不为此的数据
     *
     * @param id   角色ID
     * @param name 角色名
     * @return 符合条件的角色数据
     */
    Role selectByRoleNameNotId(Long id, String name);

    /**
     * 查询角色列表
     *
     * @param roleQuery 查询条件
     * @param pageNum   页码
     * @param pageSize  每页记录数
     * @return 角色列表
     */
    PageData<Role> list(RoleQuery roleQuery, Integer pageNum, Integer pageSize);
}
