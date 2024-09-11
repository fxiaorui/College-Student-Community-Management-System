package com.example.college.student.community.management.system.pojo.query.permission;

import com.example.college.student.community.management.system.common.annotation.QueryName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 查询权限查询参数
 */
@Data
@Accessors(chain = true)
public class PermissionQuery {

    /**
     * 权限名
     */
    @QueryName(value = "name", isFuzzy = true)
    private String name;

}
