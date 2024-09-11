package com.example.college.student.community.management.system.dao.cache.impl;

import com.example.college.student.community.management.system.common.po.UserLoginInfoPO;
import com.example.college.student.community.management.system.dao.cache.UserCacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * 用户数据(缓存)仓库实现
 */
@Slf4j
@Repository
public class UserCacheRepositoryImpl implements UserCacheRepository {

    @Value("${background-service.jwt.duration-in-minute}")
    private long durationInMinute;

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;


    @Override
    public void saveLoginInfo(String jwt, UserLoginInfoPO userLoginInfoPO) {
        String key = USER_JWT_PREFIX + jwt;
        ValueOperations<String, Serializable> opsForValue = redisTemplate.opsForValue();
        opsForValue.set(key, userLoginInfoPO, durationInMinute, TimeUnit.MINUTES);
    }

    @Override
    public void saveEnableByUserId(Long userId, Integer enable) {
        String key = USER_ENABLE_PREFIX + userId;
        ValueOperations<String, Serializable> opsForValue = redisTemplate.opsForValue();
        opsForValue.set(key, enable, durationInMinute, TimeUnit.MINUTES);
    }

    @Override
    public Boolean deleteLoginInfo(String jwt) {
        return redisTemplate.delete(USER_JWT_PREFIX + jwt);
    }

    @Override
    public Boolean deleteEnableByUserId(Long userId) {
        return redisTemplate.delete(USER_ENABLE_PREFIX + userId);
    }

    @Override
    public UserLoginInfoPO getLoginInfo(String jwt) {
        String key = USER_JWT_PREFIX + jwt;
        ValueOperations<String, Serializable> opsForValue = redisTemplate.opsForValue();
        Serializable serializable = opsForValue.get(key);
        return (UserLoginInfoPO) serializable;
    }

    @Override
    public Integer getEnableByUserId(Long userId) {
        String key = USER_ENABLE_PREFIX + userId;
        ValueOperations<String, Serializable> opsForValue = redisTemplate.opsForValue();
        Serializable serializable = opsForValue.get(key);
        return (Integer) serializable;
    }

}