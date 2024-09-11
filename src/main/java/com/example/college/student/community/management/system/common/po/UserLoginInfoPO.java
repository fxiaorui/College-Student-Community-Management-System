package com.example.college.student.community.management.system.common.po;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录信息的PO，此类数据将存入到Redis中
 */
@Data
public class UserLoginInfoPO implements Serializable {

    /**
     * 用户登录时使用的浏览器的信息
     */
    private String userAgent;

    /**
     * 用户登录时的IP地址
     */
    private String ip;

    /**
     * 权限列表的JSON字符串
     */
    private String authoritiesJsonString;

}
