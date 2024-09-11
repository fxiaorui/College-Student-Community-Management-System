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

    private final Long deptId;

    private final String nickname;


    public CustomUserDetails(Long id,
                             Long deptId,
                             String nickname,
                             String username,
                             String password,
                             boolean status,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, status, true, true, true, authorities);
        this.id = id;
        this.deptId = deptId;
        this.nickname = nickname;
    }

}
