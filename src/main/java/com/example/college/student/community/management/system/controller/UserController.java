package com.example.college.student.community.management.system.controller;

import com.example.college.student.community.management.system.common.constants.HttpConstants;
import com.example.college.student.community.management.system.common.constants.ServiceConstants;
import com.example.college.student.community.management.system.common.web.JsonResult;
import com.example.college.student.community.management.system.core.security.CurrentPrincipal;
import com.example.college.student.community.management.system.pojo.param.user.*;
import com.example.college.student.community.management.system.pojo.query.user.UserQuery;
import com.example.college.student.community.management.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * 用户业务
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController implements HttpConstants, ServiceConstants {

    @Value("${background-service.service.common.select.default-pageSize}")
    private String pageSize;

    @Autowired
    private UserService userService;


    /**
     * 用户登录
     */
    @PostMapping("/login")
    public JsonResult login(@Validated UserLoginParam userLoginParam, HttpServletRequest request) {
        log.debug("【用户登录】, 参数: {}", userLoginParam);
        return JsonResult.ok(userService.login(userLoginParam, request.getRemoteAddr(), request.getHeader(HEADER_USER_AGENT)));
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public JsonResult logout(@AuthenticationPrincipal CurrentPrincipal currentPrincipal,
                             HttpServletRequest request) {
        log.debug("【退出登录】, 当事人: {}", currentPrincipal);
        userService.logout(currentPrincipal, request.getHeader(HEADER_AUTHORIZATION));
        return JsonResult.ok();
    }

    /**
     * 退出登录(使用ID)
     */
    @PostMapping("/logout/{id}")
    public JsonResult logoutById(@PathVariable Long id) {
        log.debug("【退出登录】, id: {}", id);
        userService.logout(id);
        return JsonResult.ok();
    }

    /**
     * 用户注册
     */
    @PostMapping("/reg")
    public JsonResult reg(@Validated UserRegParam userRegParam) {
        log.debug("【用户注册】, 参数{}", userRegParam);
        userService.reg(userRegParam);
        return JsonResult.ok();
    }

    /**
     * 修改密码
     */
    @PostMapping("/rePassword")
    @PreAuthorize("hasAuthority('users/rePassword')")
    public JsonResult rePassword(@Validated RePasswordParam rePasswordParam) {
        log.debug("【修改密码】, 参数{}", rePasswordParam);
        userService.rePassword(rePasswordParam);
        return JsonResult.ok();
    }

    /**
     * 新增用户
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority('users/add')")
    public JsonResult add(@Validated UserAddParam userAddParam,
                          @AuthenticationPrincipal CurrentPrincipal currentPrincipal) {
        log.debug("【新增用户】, 参数{}", userAddParam);
        userService.add(userAddParam, currentPrincipal);
        return JsonResult.ok();
    }

    /**
     * 删除用户
     */
    @PostMapping("/del/{id}")
    @PreAuthorize("hasAuthority('users/del')")
    public JsonResult del(@PathVariable @NotNull Long id) {
        log.debug("【删除用户】, ID{}", id);
        userService.delete(id);
        return JsonResult.ok();
    }

    /**
     * 编辑用户
     */
    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('users/edit')")
    public JsonResult edit(@Validated UserEditParam userEditParam,
                           @AuthenticationPrincipal CurrentPrincipal currentPrincipal) {
        log.debug("【编辑用户】, 参数{}", userEditParam);
        userService.edit(userEditParam, currentPrincipal);
        return JsonResult.ok();
    }

    /**
     * 启用用户
     */
    @PostMapping("/enable/{id}")
    @PreAuthorize("hasAuthority('users/edit')")
    public JsonResult enable(@PathVariable @NotNull Long id,
                             @AuthenticationPrincipal CurrentPrincipal currentPrincipal) {
        log.debug("【启用用户】, id: {}", id);
        userService.upStatus(STATUS_ENABLE, id, currentPrincipal);
        return JsonResult.ok();
    }

    /**
     * 禁用用户
     */
    @PostMapping("/disable/{id}")
    @PreAuthorize("hasAuthority('users/edit')")
    public JsonResult disable(@PathVariable @NotNull Long id,
                              @AuthenticationPrincipal CurrentPrincipal currentPrincipal) {
        log.debug("【禁用用户】, id: {}", id);
        userService.upStatus(STATUS_DISABLE, id, currentPrincipal);
        return JsonResult.ok();
    }

    /**
     * 查询用户详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('users/edit')")
    public JsonResult detail(@PathVariable @NotNull Long id) {
        log.debug("【查询用户详情】, 参数{}", id);
        return JsonResult.ok(userService.detail(id));
    }

    /**
     * 查询用户列表
     */
    @GetMapping("")
    @PreAuthorize("hasAuthority('users/list')")
    public JsonResult list(UserQuery userQuery, Integer pageNum, Integer pageSize) {
        log.debug("【查询用户列表】, 查询参数: {}, 页码：{}, 每页记录数: {}", userQuery, pageNum, pageSize);
        if (SELECT_LIST_ALL_PAGE_SIZE.equals(pageSize)) {
            return JsonResult.ok(userService.list(userQuery));
        }
        pageNum = pageNum == null || pageNum == 0 ? SELECT_LIST_HOME_PAGE_NUM : pageNum;
        pageSize = pageSize == null || pageSize == 0 ? Integer.parseInt(this.pageSize) : pageSize;
        return JsonResult.ok(userService.list(userQuery, pageNum, pageSize));
    }
}
