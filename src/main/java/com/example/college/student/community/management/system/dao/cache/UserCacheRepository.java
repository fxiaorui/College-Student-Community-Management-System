package com.example.college.student.community.management.system.dao.cache;


import com.example.college.student.community.management.system.common.constants.UserCacheConstants;
import com.example.college.student.community.management.system.common.po.UserLoginInfoPO;

/**
 * 用户数据(缓存)仓库
 */
public interface UserCacheRepository extends UserCacheConstants {

    /**
     * 向Redis中存入用户登录信息
     *
     * @param jwt             用户登录成功后得到的JWT
     * @param userLoginInfoPO 用户登录信息
     */
    void saveLoginInfo(String jwt, UserLoginInfoPO userLoginInfoPO);

    /**
     * 根据用户ID存入用户启用状态
     *
     * @param userId 用户ID
     * @param enable 用户启用状态
     */
    void saveEnableByUserId(Long userId, Integer enable);

    /**
     * 根据jwt删除登录信息
     *
     * @param jwt 用户登录成功后得到的JWT
     * @return 是否删除成功
     */
    Boolean deleteLoginInfo(String jwt);

    /**
     * 根据ID删除登录信息
     *
     * @param userId 用户ID
     * @return 是否删除成功
     */
    Boolean deleteEnableByUserId(Long userId);

    /**
     * 根据JWT从Redis中获取用户登录信息
     *
     * @param jwt 用户登录成功时获取的JWT
     * @return 与JWT匹配的用户登录信息，如果没有匹配的数据，则返回null
     */
    UserLoginInfoPO getLoginInfo(String jwt);

    /**
     * 根据用户ID查询用户启用状态
     *
     * @param userId 用户ID
     * @return 用户启用状态
     */
    Integer getEnableByUserId(Long userId);

}
