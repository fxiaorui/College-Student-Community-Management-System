package com.example.college.student.community.management.system.controller;

import com.example.college.student.community.management.system.common.constants.ServiceConstants;
import com.example.college.student.community.management.system.common.web.JsonResult;
import com.example.college.student.community.management.system.core.security.CurrentPrincipal;
import com.example.college.student.community.management.system.pojo.param.role.RoleAddParam;
import com.example.college.student.community.management.system.pojo.param.role.RoleEditParam;
import com.example.college.student.community.management.system.pojo.query.role.RoleQuery;
import com.example.college.student.community.management.system.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * 角色业务
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/roles")
public class RoleController implements ServiceConstants {
    /////122231

    @Value("${background-service.service.common.select.default-pageSize}")
    private String pageSize;

    @Autowired
    private RoleService roleService;


    /**
     * 新增角色
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority('roles/add')")
    public JsonResult add(@Validated RoleAddParam roleAddParam,
                          @AuthenticationPrincipal CurrentPrincipal currentPrincipal) {
        log.debug("【新增角色】, 参数: {}", roleAddParam);
        roleService.add(roleAddParam, currentPrincipal);
        return JsonResult.ok();
    }

    /**
     * 删除角色
     */
    @PostMapping("/del/{id}")
    @PreAuthorize("hasAuthority('roles/del')")
    public JsonResult del(@PathVariable @NotNull Long id) {
        log.debug("【删除角色】, 参数: {}", id);
        roleService.delete(id);
        return JsonResult.ok();
    }

    /**
     * 修改角色
     */
    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('roles/edit')")
    public JsonResult edit(@Validated RoleEditParam roleEditParam,
                           @AuthenticationPrincipal CurrentPrincipal currentPrincipal) {
        log.debug("【修改角色】, 参数: {}", roleEditParam);
        roleService.edit(roleEditParam, currentPrincipal);
        return JsonResult.ok();
    }

    /**
     * 查询角色详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('roles/detail')")
    public JsonResult detail(@PathVariable Long id) {
        log.debug("【查询角色详情】, 参数: {}", id);
        return JsonResult.ok(roleService.detail(id));
    }

    /**
     * 查询角色列表
     */
    @GetMapping("")
    @PreAuthorize("hasAuthority('roles/list')")
    public JsonResult list(RoleQuery roleQuery, Integer pageNum, Integer pageSize) {
        log.debug("【查询角色列表】, 查询参数: {}, 页码：{}, 每页记录数: {}", roleQuery, pageNum, pageSize);
        if (SELECT_LIST_ALL_PAGE_SIZE.equals(pageSize)) {
            return JsonResult.ok(roleService.list(roleQuery));
        }
        pageNum = pageNum == null || pageNum == 0 ? SELECT_LIST_HOME_PAGE_NUM : pageNum;
        pageSize = pageSize == null || pageSize == 0 ? Integer.parseInt(this.pageSize) : pageSize;
        return JsonResult.ok(roleService.list(roleQuery, pageNum, pageSize));
    }
}
