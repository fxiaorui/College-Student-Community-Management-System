package com.example.college.student.community.management.system.service;

import com.example.college.student.community.management.system.common.pojo.vo.PageData;
import com.example.college.student.community.management.system.core.security.CurrentPrincipal;
import com.example.college.student.community.management.system.pojo.entity.User;
import com.example.college.student.community.management.system.pojo.param.user.*;
import com.example.college.student.community.management.system.pojo.query.user.UserQuery;
import com.example.college.student.community.management.system.pojo.vo.user.UserDetailVO;
import com.example.college.student.community.management.system.pojo.vo.user.UserLoginResultVO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户业务服务
 */
@Transactional
public interface UserService {

    /**
     * 登录
     *
     * @param userLoginParam 登录参数
     * @param remoteAddr     用户IP地址
     * @param userAgent      用户浏览器信息
     * @return 登录返回值
     */
    UserLoginResultVO login(UserLoginParam userLoginParam, String remoteAddr, String userAgent);

    /**
     * 退出登录
     *
     * @param currentPrincipal Security提供的当事人
     * @param jwt              请求携带的JWT
     */
    void logout(CurrentPrincipal currentPrincipal, String jwt);

    /**
     * 退出登录
     *
     * @param id  ID
     * @param jwt 请求携带的JWT
     */
    void logout(Long id);

    /**
     * 注册
     *
     * @param userRegParam 注册参数
     */
    void reg(UserRegParam userRegParam);

    /**
     * 修改密码
     *
     * @param rePasswordParam 密码修改参数
     */
    void rePassword(RePasswordParam rePasswordParam);

    /**
     * 新增用户
     *
     * @param userAddParam 用户新增参数
     * @param principal    当事人
     */
    void add(UserAddParam userAddParam, CurrentPrincipal principal);

    /**
     * 根据ID删除用户
     *
     * @param id 用户ID
     */
    void delete(Long id);

    /**
     * 编辑用户
     *
     * @param userEditParam 编辑用户参数
     * @param principal     当事人
     */
    void edit(UserEditParam userEditParam, CurrentPrincipal principal);

    /**
     * 修改用户状态
     *
     * @param status    需要更改到的状态
     * @param id        科室ID
     * @param principal 当事人
     */
    void upStatus(String status, Long id, CurrentPrincipal principal);

    /**
     * 根据ID查询用户详情
     *
     * @param id 用户ID
     * @return 用户详情
     */
    UserDetailVO detail(Long id);

    /**
     * 查询用户列表(所有数据)
     *
     * @param userQuery 用户查询条件
     * @return 用户列表
     */
    PageData<User> list(UserQuery userQuery);

    /**
     * 分页查询用户列表
     *
     * @param userQuery 用户查询条件
     * @param pageNum   页码
     * @param pageSize  每页记录数
     * @return 用户列表
     */
    PageData<User> list(UserQuery userQuery, Integer pageNum, Integer pageSize);
}
