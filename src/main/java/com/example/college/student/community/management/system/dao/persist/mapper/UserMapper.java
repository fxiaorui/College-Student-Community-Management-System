package com.example.college.student.community.management.system.dao.persist.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.college.student.community.management.system.pojo.entity.User;
import com.example.college.student.community.management.system.pojo.vo.user.UserLoginInfoVO;

/**
 * 用户数据(持久)连接
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查询用户登录信息
     *
     * @param username 用户名
     * @return 用户登录信息
     */
    UserLoginInfoVO getLoginInfoByUsername(String username);
}
