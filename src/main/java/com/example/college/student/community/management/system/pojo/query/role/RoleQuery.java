package com.example.college.student.community.management.system.pojo.query.role;

import com.example.college.student.community.management.system.common.annotation.QueryName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 查询角色查询参数
 */
@Data
@Accessors(chain = true)
public class RoleQuery implements Serializable {

    /**
     * 角色名
     */
    @QueryName(value = "name", isFuzzy = true)
    private String name;

}
