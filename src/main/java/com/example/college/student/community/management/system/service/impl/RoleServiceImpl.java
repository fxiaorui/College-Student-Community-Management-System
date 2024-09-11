package com.example.college.student.community.management.system.service.impl;

import com.example.college.student.community.management.system.common.constants.ServiceConstants;
import com.example.college.student.community.management.system.common.ex.ServiceException;
import com.example.college.student.community.management.system.common.pojo.vo.PageData;
import com.example.college.student.community.management.system.common.web.ServiceCode;
import com.example.college.student.community.management.system.core.security.CurrentPrincipal;
import com.example.college.student.community.management.system.dao.persist.repository.RolePermissionRepository;
import com.example.college.student.community.management.system.dao.persist.repository.RoleRepository;
import com.example.college.student.community.management.system.dao.persist.repository.UserRoleRepository;
import com.example.college.student.community.management.system.pojo.entity.Role;
import com.example.college.student.community.management.system.pojo.entity.RolePermission;
import com.example.college.student.community.management.system.pojo.param.role.RoleAddParam;
import com.example.college.student.community.management.system.pojo.param.role.RoleEditParam;
import com.example.college.student.community.management.system.pojo.query.role.RoleQuery;
import com.example.college.student.community.management.system.pojo.vo.role.RoleDetailVO;
import com.example.college.student.community.management.system.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 角色业务服务实现
 */
@Slf4j
@Service
public class RoleServiceImpl implements RoleService, ServiceConstants {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;


    @Override
    public void add(RoleAddParam roleAddParam, CurrentPrincipal principal) {
        {
            if (roleRepository.selectByName(roleAddParam.getName()) != null) {
                String message = "新增角色失败, 角色名已存在!";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
            }
        }

        Role role = new Role();
        BeanUtils.copyProperties(roleAddParam, role);
        role.setCreateBy(principal.getId());
        if (
                roleRepository.insert(role) != 1 ||
                        insertRolePermissionList(role.getId(), roleAddParam.getPermissionIds()) < 1
        ) {
            String message = "新增角色失败, 服务器忙, 请稍后重试!";
            log.error(message);
            throw new ServiceException(ServiceCode.ERROR_INSERT, message);
        }
    }

    @Override
    public void delete(Long id) {
        {
            if (roleRepository.selectById(id) == null) {
                String message = "删除角色失败, 该角色不存在!";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
            }
            if (!userRoleRepository.selectByRoleId(id).isEmpty()) {
                String message = "删除角色失败, 该角色仍有用户使用!";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
            }
        }

        if (
                roleRepository.deleteById(id) != 1 ||
                        rolePermissionRepository.deleteByRoleId(id) < 1
        ) {
            String message = "删除角色失败, 服务器忙, 请稍后重试!";
            log.error(message);
            throw new ServiceException(ServiceCode.ERROR_DELETE, message);
        }
    }

    @Override
    public void edit(RoleEditParam roleEditParam, CurrentPrincipal principal) {
        {
            if (roleRepository.selectById(roleEditParam.getId()) == null) {
                String message = "编辑角色失败, 该角色不存在!";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
            }
            if (roleRepository.selectByRoleNameNotId(roleEditParam.getId(), roleEditParam.getName()) != null) {
                String message = "修改角色失败, 角色名已存在!";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
            }
        }

        Role role = new Role();
        BeanUtils.copyProperties(roleEditParam, role);
        role.setUpdateBy(principal.getId());
        if (
                roleRepository.updateById(role) != 1 ||
                        rolePermissionRepository.deleteByRoleId(roleEditParam.getId()) < 1 ||
                        insertRolePermissionList(role.getId(), roleEditParam.getPermissionIds()) < 1
        ) {
            String message = "修改角色失败, 服务器忙, 请稍后重试!";
            log.error(message);
            throw new ServiceException(ServiceCode.ERROR_UPDATE, message);
        }
    }

    @Override
    public RoleDetailVO detail(Long id) {
        Role role = roleRepository.selectById(id);
        if (role == null) {
            String message = "获取角色数据失败, 该角色不存在!";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        RoleDetailVO roleDetailVO = new RoleDetailVO();
        BeanUtils.copyProperties(role, roleDetailVO);
        roleDetailVO.setPermissionIds(
                rolePermissionRepository.selectByRoleId(id)
                        .stream()
                        .map(RolePermission::getPermissionId)
                        .toArray(Long[]::new)
        );
        return roleDetailVO;
    }

    @Override
    public PageData<Role> list(RoleQuery roleQuery) {
        return list(roleQuery, SELECT_LIST_HOME_PAGE_NUM, Integer.MAX_VALUE);
    }

    @Override
    public PageData<Role> list(RoleQuery roleQuery, Integer pageNum, Integer pageSize) {
        return roleRepository.list(roleQuery, pageNum, pageSize);
    }

    /**
     * 插入权限列表
     *
     * @param roleId        角色ID
     * @param permissionIds 权限ID列表
     * @return 影响的数据行数
     */
    private int insertRolePermissionList(Long roleId, Long[] permissionIds) {
        return rolePermissionRepository.insertBatch(
                Stream.of(permissionIds)
                        .map(permissionId -> new RolePermission()
                                .setRoleId(roleId)
                                .setPermissionId(permissionId)
                        )
                        .collect(Collectors.toList())
        );
    }
}
