package com.example.college.student.community.management.system.dao.persist.repository.inpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.college.student.community.management.system.common.pojo.vo.PageData;
import com.example.college.student.community.management.system.common.util.PageInfoToPageDataConverter;
import com.example.college.student.community.management.system.common.util.QueryToQueryWrapperConverter;
import com.example.college.student.community.management.system.dao.persist.mapper.PermissionMapper;
import com.example.college.student.community.management.system.dao.persist.repository.PermissionRepository;
import com.example.college.student.community.management.system.pojo.entity.Permission;
import com.example.college.student.community.management.system.pojo.query.permission.PermissionQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限数据(持久)仓库实现
 */
@Slf4j
@Repository
public class PermissionRepositoryImpl implements PermissionRepository {

    @Autowired
    private PermissionMapper permissionMapper;


    @Override
    public List<Permission> list(PermissionQuery permissionQuery) {
        log.debug("【查询权限列表】, 查询参数: {}", permissionQuery);
        return permissionMapper.selectList(null);
    }

    @Override
    public PageData<Permission> list(PermissionQuery permissionQuery, Integer pageNum, Integer pageSize) {
        log.debug("【查询权限列表】, 查询参数: {}, 页码：{}，每页记录数：{}", permissionQuery, pageNum, pageSize);
        QueryWrapper<Permission> wrapper = QueryToQueryWrapperConverter.convert(permissionQuery);
        PageHelper.startPage(pageNum, pageSize);
        return PageInfoToPageDataConverter.convert(new PageInfo<>(permissionMapper.selectList(wrapper)));
    }
}
