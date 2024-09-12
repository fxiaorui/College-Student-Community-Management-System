package com.example.college.student.community.management.system.dao.persist.repository;

import com.example.college.student.community.management.system.common.pojo.vo.PageData;
import com.example.college.student.community.management.system.pojo.entity.User;
import com.example.college.student.community.management.system.pojo.query.user.UserQuery;
import com.example.college.student.community.management.system.pojo.vo.user.UserLoginInfoVO;

/**
 * 用户数据(持久)仓库实现
 */
public interface UserRepository {

    /**
     * 插入一条用户数据
     *
     * @param user 用户数据
     * @return 影响的数据行数
     */
    int insert(User user);

    /**
     * 根据ID删除用户
     *
     * @param id 用户ID
     * @return 影响的数据行数
     */
    int deleteById(Long id);

    /**
     * 根据ID修改用户数据
     *
     * @param user 修改的科室数据(包含科室ID)
     * @return 影响的数据行数
     */
    int updateById(User user);

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    User selectById(Long userId);

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    User selectByStudentId(String username);

    /**
     * 根据学号查询ID不为此的数据
     *
     * @param id   ID
     * @param name 用户名
     * @return 符合条件的科室数据
     */
    User selectByStudentIdNotId(Long id, String name);

    /**
     * 根据用户名查询用户登录信息
     *
     * @param username 用户名
     * @return 用户登录信息
     */
    UserLoginInfoVO getLoginInfoByUsername(String username);

    /**
     * 查询用户列表
     *
     * @param userQuery 用户查询条件
     * @param pageNum   页码
     * @param pageSize  每页记录数
     * @return 用户列表
     */
    PageData<User> selectList(UserQuery userQuery, Integer pageNum, Integer pageSize);
}
