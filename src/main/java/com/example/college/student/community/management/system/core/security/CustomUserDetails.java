package com.example.college.student.community.management.system.core.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 自定义的用户详情类
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CustomUserDetails extends User {

    private final Long id;

    private final String studentId;

    private final String name;

    private final String sex;

    private final String email;

    private final String major;

    private final String classId;


    public CustomUserDetails(Long id,
                             String studentId,
                             String password,
                             String name,
                             String sex,
                             String email,
                             String major,
                             String classId,
                             boolean status,
                             Collection<? extends GrantedAuthority> authorities) {
        super(studentId, password, status, true, true, true, authorities);
        this.id = id;
        this.studentId = studentId;
        this.name = name;
        this.sex = sex;
        this.email = email;
        this.major = major;
        this.classId = classId;

    }

}
