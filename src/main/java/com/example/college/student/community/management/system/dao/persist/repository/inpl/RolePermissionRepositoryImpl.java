package com.example.college.student.community.management.system.dao.persist.repository.inpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.college.student.community.management.system.dao.persist.mapper.RolePermissionMapper;
import com.example.college.student.community.management.system.dao.persist.repository.RolePermissionRepository;
import com.example.college.student.community.management.system.pojo.entity.RolePermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 角色-权限关联数据(持久)仓库实现
 */
@Slf4j
@Repository
public class RolePermissionRepositoryImpl implements RolePermissionRepository {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;


    @Override
    public int insertBatch(List<RolePermission> rolePermissionList) {
        log.debug("【批量插入角色-权限关联数据】, 数据列表: {}", rolePermissionList);
        return rolePermissionMapper.insertBatch(rolePermissionList);
    }

    @Override
    public int deleteByRoleId(Long roleId) {
        log.debug("【删除角色-权限关联数据】, 角色ID: {}", roleId);
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        return rolePermissionMapper.delete(wrapper);
    }

    @Override
    public List<RolePermission> selectByRoleId(Long id) {
        log.debug("【查询角色-权限关联数据】, 角色ID: {}", id);
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", id);
        return rolePermissionMapper.selectList(wrapper);
    }

}
