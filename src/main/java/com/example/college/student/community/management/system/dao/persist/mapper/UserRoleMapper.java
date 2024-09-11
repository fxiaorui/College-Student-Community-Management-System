package com.example.college.student.community.management.system.dao.persist.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.college.student.community.management.system.pojo.entity.UserRole;

import java.util.List;

/**
 * 用户-角色关联数据(持久)连接
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 批量插入用户-角色关联数据
     *
     * @param userRoleList 用户-角色关联数据
     * @return 影响的数据行数
     */
    int insertBatch(List<UserRole> userRoleList);
}
