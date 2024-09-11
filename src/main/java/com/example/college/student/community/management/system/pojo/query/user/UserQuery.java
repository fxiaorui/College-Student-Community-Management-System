package com.example.college.student.community.management.system.pojo.query.user;

import com.example.college.student.community.management.system.common.annotation.QueryName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 查询用户查询参数
 */
@Data
@Accessors(chain = true)
public class UserQuery {

    /**
     * 用户名
     */
    @QueryName(value = "username", isFuzzy = true)
    private String username;

    /**
     * 昵称
     */
    @QueryName(value = "nickname", isFuzzy = true)
    private String nickname;

    /**
     * 性别
     */
    @QueryName("sex")
    private String sex;

    /**
     * 状态
     */
    @QueryName("status")
    private String status;
}
