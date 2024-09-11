package com.example.college.student.community.management.system.dao.persist.repository.inpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.college.student.community.management.system.common.pojo.vo.PageData;
import com.example.college.student.community.management.system.common.util.PageInfoToPageDataConverter;
import com.example.college.student.community.management.system.common.util.QueryToQueryWrapperConverter;
import com.example.college.student.community.management.system.dao.persist.mapper.RoleMapper;
import com.example.college.student.community.management.system.dao.persist.repository.RoleRepository;
import com.example.college.student.community.management.system.pojo.entity.Role;
import com.example.college.student.community.management.system.pojo.query.role.RoleQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 角色数据(持久)仓库实现
 */
@Slf4j
@Repository
public class RoleRepositoryImpl implements RoleRepository {

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public int insert(Role role) {
        log.debug("【插入角色】, 参数: {}", role);
        return roleMapper.insert(role);
    }

    @Override
    public int deleteById(Long id) {
        return roleMapper.deleteById(id);
    }

    @Override
    public int updateById(Role role) {
        log.debug("【修改角色】, 参数: {}", role);
        return roleMapper.updateById(role);
    }

    @Override
    public Role selectById(Long id) {
        log.debug("【查询角色信息】, ID: {}", id);
        return roleMapper.selectById(id);
    }

    @Override
    public Role selectByName(String name) {
        log.debug("【查询角色数据】, 角色名: {}", name);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        return roleMapper.selectOne(wrapper);
    }

    @Override
    public Role selectByRoleNameNotId(Long id, String name) {
        log.debug("【查询角色】,  !ID: {}, 名称: {}", id, name);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.ne("id", id);
        wrapper.eq("name", name);
        return roleMapper.selectOne(wrapper);
    }

    @Override
    public PageData<Role> list(RoleQuery roleQuery, Integer pageNum, Integer pageSize) {
        log.debug("【查询角色列表】, 查询参数: {}, 页码：{}，每页记录数：{}", roleQuery, pageNum, pageSize);
        QueryWrapper<Role> wrapper = QueryToQueryWrapperConverter.convert(roleQuery);
        PageHelper.startPage(pageNum, pageSize);
        wrapper.orderByDesc("sort","id");
        return PageInfoToPageDataConverter.convert(new PageInfo<>(roleMapper.selectList(wrapper)));
    }
}
