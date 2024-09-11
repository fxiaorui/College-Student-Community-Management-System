package com.example.college.student.community.management.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.college.student.community.management.system.common.constants.ServiceConstants;
import com.example.college.student.community.management.system.common.ex.ServiceException;
import com.example.college.student.community.management.system.common.po.UserLoginInfoPO;
import com.example.college.student.community.management.system.common.pojo.vo.PageData;
import com.example.college.student.community.management.system.common.web.ServiceCode;
import com.example.college.student.community.management.system.core.security.CurrentPrincipal;
import com.example.college.student.community.management.system.core.security.CustomUserDetails;
import com.example.college.student.community.management.system.dao.cache.UserCacheRepository;
import com.example.college.student.community.management.system.dao.persist.repository.UserRepository;
import com.example.college.student.community.management.system.dao.persist.repository.UserRoleRepository;
import com.example.college.student.community.management.system.pojo.entity.User;
import com.example.college.student.community.management.system.pojo.entity.UserRole;
import com.example.college.student.community.management.system.pojo.param.user.*;
import com.example.college.student.community.management.system.pojo.query.user.UserQuery;
import com.example.college.student.community.management.system.pojo.vo.user.UserDetailVO;
import com.example.college.student.community.management.system.pojo.vo.user.UserLoginResultVO;
import com.example.college.student.community.management.system.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 用户业务服务实现
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService, ServiceConstants {

    @Value("${background-service.jwt.secret-key}")
    private String secretKey;

    @Value("${background-service.jwt.duration-in-minute}")
    private Long durationInMinute;

    @Value("${background-service.service.user.default-password}")
    private String defaultPassword;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserCacheRepository userCacheRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserLoginResultVO login(UserLoginParam userLoginParam, String remoteAddr, String userAgent) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userLoginParam.getUsername(),
                userLoginParam.getPassword()
        );
        Authentication authenticateResult = authenticationManager.authenticate(authentication);
        log.debug("验证用户登录成功，返回的认证结果：{}", authenticateResult);

        CustomUserDetails userDetails = (CustomUserDetails) authenticateResult.getPrincipal();
        Long id = userDetails.getId();
        Long deptId = userDetails.getDeptId();
        String username = userDetails.getUsername();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        String authoritiesJsonString = JSON.toJSONString(authorities);

        Date date = new Date(System.currentTimeMillis() + 60L * 1000 * durationInMinute);
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("username", username);
        claims.put("deptId", deptId);
        // 生成JWT时，不再存入权限列表
        // claims.put("authoritiesJsonString", authoritiesJsonString);
        String jwt = Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        // 生成JWT之后，需要将权限列表存入到Redis中
        UserLoginInfoPO userLoginInfoPO = new UserLoginInfoPO();
        userLoginInfoPO.setUserAgent(userAgent);
        userLoginInfoPO.setIp(remoteAddr);
        userLoginInfoPO.setAuthoritiesJsonString(authoritiesJsonString);
        userCacheRepository.saveLoginInfo(jwt, userLoginInfoPO);

        // 将用户状态存入到Redis中
        userCacheRepository.saveEnableByUserId(id, 1);

        return new UserLoginResultVO()
                .setId(id)
                .setNickname(userDetails.getNickname())
                .setUsername(username)
                .setToken(jwt);
    }

    @Override
    public void logout(CurrentPrincipal currentPrincipal, String jwt) {
        userCacheRepository.deleteEnableByUserId(currentPrincipal.getId());
        userCacheRepository.deleteLoginInfo(jwt);
    }

    @Override
    public void logout(Long id) {
        userCacheRepository.deleteEnableByUserId(id);
    }

    @Override
    public void reg(UserRegParam userRegParam) {
        {
            if (userRepository.selectByUsername(userRegParam.getUsername()) != null) {
                String message = "注册失败，用户名已经被占用!";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
            }
        }

        User user = new User();
        BeanUtils.copyProperties(userRegParam, user);
        user.setStatus("1").setPassword(passwordEncoder.encode(user.getPassword()));
        if (userRepository.insert(user) != 1) {
            String message = "注册失败，服务器忙，请稍后重试!";
            log.error(message);
            throw new ServiceException(ServiceCode.ERROR_INSERT, message);
        }

        UserRole userRole = new UserRole().setUserId(user.getId()).setRoleId(2L);
        if (userRoleRepository.insert(userRole) != 1) {
            String message = "注册失败，服务器忙，请稍后重试!";
            log.error(message);
            throw new ServiceException(ServiceCode.ERROR_INSERT, message);
        }
    }

    @Override
    public void rePassword(RePasswordParam rePasswordParam) {
        User user = userRepository.selectById(rePasswordParam.getUserId());
        if (user == null) {
            String message = "修改密码失败, 该用户不存在!";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        if (!passwordEncoder.matches(rePasswordParam.getPassword(), user.getPassword())) {
            String message = "修改密码失败, 密码错误!";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_DATA, message);
        }

        user.setPassword(passwordEncoder.encode(rePasswordParam.getNewPassword()));
        if (userRepository.updateById(user) != 1) {
            String message = "修改密码失败，服务器忙，请稍后重试!";
            log.error(message);
            throw new ServiceException(ServiceCode.ERROR_UPDATE, message);
        }
    }

    @Override
    public void add(UserAddParam userAddParam, CurrentPrincipal currentPrincipal) {
        {
            if (userRepository.selectByUsername(userAddParam.getUsername()) != null) {
                String message = "新增用户失败, 用户名已经被占用!";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
            }
        }

        User user = new User();
        BeanUtils.copyProperties(userAddParam, user);
        user.setPassword(passwordEncoder.encode(defaultPassword)).setStatus(STATUS_ENABLE).setCreateBy(currentPrincipal.getId());
        if (
                userRepository.insert(user) != 1 ||
                        insertUserRoleList(user.getId(), userAddParam.getRoleIds()) < 1
        ) {
            String message = "新增用户失败，服务器忙，请稍后重试!";
            log.error(message);
            throw new ServiceException(ServiceCode.ERROR_INSERT, message);
        }
    }

    @Override
    public void delete(Long id) {
        {
            if (id == 1L) {
                String message = "删除用户失败, 您无权限删除此用户!";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_FORBIDDEN, message);
            }

            if (userRepository.selectById(id) == null) {
                String message = "删除用户失败, 用户不存在!";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
            }
        }

        userCacheRepository.saveEnableByUserId(id, 0);
        if (userRepository.deleteById(id) != 1) {
            String message = "删除用户失败，服务器忙，请稍后重试!";
            log.error(message);
            throw new ServiceException(ServiceCode.ERROR_INSERT, message);
        }
    }

    @Override
    public void edit(UserEditParam userEditParam, CurrentPrincipal principal) {
        {
            if (userRepository.selectById(userEditParam.getId()) == null) {
                String message = "删除用户失败, 用户不存在!";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
            }
            if (userRepository.selectByUsernameNotId(userEditParam.getId(), userEditParam.getUsername()) != null) {
                String message = "修改用户失败, 用户名已经被占用!";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
            }
        }

        User user = new User();
        BeanUtils.copyProperties(userEditParam, user);
        user.setUpdateBy(principal.getId());
        if (
                userRepository.updateById(user) != 1 ||
                        userRoleRepository.deleteByUserId(userEditParam.getId()) < 1 ||
                        insertUserRoleList(user.getId(), userEditParam.getRoleIds()) < 1
        ) {
            String message = "修改角色失败, 服务器忙, 请稍后重试!";
            log.error(message);
            throw new ServiceException(ServiceCode.ERROR_UPDATE, message);
        }
    }

    @Override
    public void upStatus(String status, Long id, CurrentPrincipal currentPrincipal) {
        {
            User user = userRepository.selectById(id);
            if (user == null) {
                String message = "编辑用户失败, 用户不存在!";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
            }
            if (status.equals(user.getStatus())) {
                String message = "编辑用户失败, 该用户状态冲突!";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
            }
            if (id.equals(currentPrincipal.getId())) {
                String message = "编辑用户失败, 不可" + (STATUS_DISABLE.equals(status) ? "禁用" : "启用") + "当前用户!";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
            }
        }

        User user = new User();
        user.setUpdateBy(currentPrincipal.getId());
        user.setId(id).setStatus(status);
        if (userRepository.updateById(user) != 1) {
            String message = "编辑用户失败, 服务器忙, 请稍后重试!";
            log.error(message);
            throw new ServiceException(ServiceCode.ERROR_UPDATE, message);
        }
    }

    @Override
    public UserDetailVO detail(Long id) {
        User user = userRepository.selectById(id);
        if (user == null) {
            String message = "获取用户数据失败, 用户不存在!";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        UserDetailVO userDetailVO = new UserDetailVO();
        BeanUtils.copyProperties(user, userDetailVO);
        userDetailVO.setRoleIds(
                userRoleRepository.selectByUserId(id)
                        .stream()
                        .map(UserRole::getRoleId)
                        .toArray(Long[]::new)
        );
        return userDetailVO;
    }

    @Override
    public PageData<User> list(UserQuery userQuery) {
        return list(userQuery, SELECT_LIST_HOME_PAGE_NUM, Integer.MAX_VALUE);
    }

    @Override
    public PageData<User> list(UserQuery userQuery, Integer pageNum, Integer pageSize) {
        return userRepository.selectList(userQuery, pageNum, pageSize);
    }

    /**
     * 插入角色列表
     *
     * @param userid  用户ID
     * @param roleIds 权限ID列表
     * @return 影响的数据行数
     */
    private int insertUserRoleList(Long userid, Long[] roleIds) {
        return userRoleRepository.insertBatch(
                Stream.of(roleIds)
                        .map(roleId -> new UserRole()
                                .setUserId(userid)
                                .setRoleId(roleId))
                        .collect(Collectors.toList())
        );
    }
}
