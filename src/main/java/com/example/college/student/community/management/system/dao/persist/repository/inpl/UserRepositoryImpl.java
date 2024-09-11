package com.example.college.student.community.management.system.dao.persist.repository.inpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.college.student.community.management.system.common.pojo.vo.PageData;
import com.example.college.student.community.management.system.common.util.PageInfoToPageDataConverter;
import com.example.college.student.community.management.system.common.util.QueryToQueryWrapperConverter;
import com.example.college.student.community.management.system.dao.persist.mapper.UserMapper;
import com.example.college.student.community.management.system.dao.persist.repository.UserRepository;
import com.example.college.student.community.management.system.pojo.entity.User;
import com.example.college.student.community.management.system.pojo.query.user.UserQuery;
import com.example.college.student.community.management.system.pojo.vo.user.UserLoginInfoVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 用户数据(持久)仓库实现
 */
@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private UserMapper userMapper;


    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int deleteById(Long id) {
        log.debug("【删除用户】, ID: {}", id);
        return userMapper.deleteById(id);
    }

    @Override
    public int updateById(User user) {
        log.debug("【修改用户】, 参数: {}", user);
        return userMapper.updateById(user);
    }

    @Override
    public User selectById(Long userId) {
        log.debug("【查询用户信息】, 用户ID: {}", userId);
        return userMapper.selectById(userId);
    }

    @Override
    public User selectByUsername(String username) {
        log.debug("【查询用户信息】, 用户名: {}", username);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public User selectByUsernameNotId(Long id, String name) {
        log.debug("【查询用户】, !ID: {}, username: {}", id, name);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.ne("id", id);
        wrapper.eq("username", name);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public UserLoginInfoVO getLoginInfoByUsername(String username) {
        log.debug("【查询用户登录信息】, 用户名: {}", username);
        return userMapper.getLoginInfoByUsername(username);
    }

    @Override
    public PageData<User> selectList(UserQuery userQuery, Integer pageNum, Integer pageSize) {
        log.debug("【查询用户列表】, 查询参数: {}, 页码：{}，每页记录数：{}", userQuery, pageNum, pageSize);
        QueryWrapper<User> wrapper = QueryToQueryWrapperConverter.convert(userQuery);
        wrapper.orderByDesc("id");
        PageHelper.startPage(pageNum, pageSize);
        return PageInfoToPageDataConverter.convert(new PageInfo<>(userMapper.selectList(wrapper)));
    }
}
