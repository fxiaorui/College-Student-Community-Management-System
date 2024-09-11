package com.example.college.student.community.management.system.dao.persist.repository.inpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.college.student.community.management.system.dao.persist.mapper.UserRoleMapper;
import com.example.college.student.community.management.system.dao.persist.repository.UserRoleRepository;
import com.example.college.student.community.management.system.pojo.entity.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户-角色关联数据(持久)仓库实现
 */
@Slf4j
@Repository
public class UserRoleRepositoryImpl implements UserRoleRepository {

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public int insert(UserRole userRole) {
        log.debug("【插入用户-角色关联数据】, 参数: {}", userRole);
        return userRoleMapper.insert(userRole);
    }

    @Override
    public int insertBatch(List<UserRole> userRoleList) {
        log.debug("【批量插入用户-角色关联数据】, 数据列表: {}", userRoleList);
        return userRoleMapper.insertBatch(userRoleList);
    }

    @Override
    public int deleteByUserId(Long id) {
        log.debug("【删除用户-角色关联数据】, 用户ID: {}", id);
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", id);
        return userRoleMapper.delete(wrapper);
    }

    @Override
    public List<UserRole> selectByUserId(Long id) {
        log.debug("【查询用户-角色关联数据】, 用户ID: {}", id);
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", id);
        return userRoleMapper.selectList(wrapper);
    }

    @Override
    public List<UserRole> selectByRoleId(Long id) {
        log.debug("【查询用户-角色关联数据】, 角色ID: {}", id);
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", id);
        return userRoleMapper.selectList(wrapper);
    }
}
