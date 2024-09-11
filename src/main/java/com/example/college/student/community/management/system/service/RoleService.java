package com.example.college.student.community.management.system.service;

import com.example.college.student.community.management.system.common.pojo.vo.PageData;
import com.example.college.student.community.management.system.core.security.CurrentPrincipal;
import com.example.college.student.community.management.system.pojo.entity.Role;
import com.example.college.student.community.management.system.pojo.param.role.RoleAddParam;
import com.example.college.student.community.management.system.pojo.param.role.RoleEditParam;
import com.example.college.student.community.management.system.pojo.query.role.RoleQuery;
import com.example.college.student.community.management.system.pojo.vo.role.RoleDetailVO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色业务服务
 */
@Transactional
public interface RoleService {

    /**
     * 新增角色
     *
     * @param roleAddParam 角色新增参数
     * @param principal 当事人
     */
    void add(RoleAddParam roleAddParam, CurrentPrincipal principal);

    /**
     * 根据ID删除角色
     *
     * @param id 角色ID
     */
    void delete(Long id);

    /**
     * 编辑角色
     *
     * @param roleEditParam 角色修改参数
     * @param principal 当事人
     */
    void edit(RoleEditParam roleEditParam, CurrentPrincipal principal);

    /**
     * 根据ID查询角色详情
     *
     * @param id 角色ID
     * @return 角色详情
     */
    RoleDetailVO detail(Long id);

    /**
     * 查询角色列表(所有数据)
     *
     * @param roleQuery 查询条件
     * @return 角色列表
     */
    PageData<Role> list(RoleQuery roleQuery);

    /**
     * 分页查询角色列表
     *
     * @param roleQuery 查询条件
     * @param pageNum   页码
     * @param pageSize  每页记录数
     * @return 角色列表
     */
    PageData<Role> list(RoleQuery roleQuery, Integer pageNum, Integer pageSize);
}
