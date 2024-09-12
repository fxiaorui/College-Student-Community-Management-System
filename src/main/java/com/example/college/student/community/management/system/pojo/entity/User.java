package com.example.college.student.community.management.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.college.student.community.management.system.common.pojo.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户实体类
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class User extends Entity {

    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 学号
     */
    private String studentId;

    /**
     * 密码(密文)
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 专业
     */
    private String major;

    /**
     * 班级
     */
    private String classId;

    /**
     * 状态
     */
    private String status;
}
