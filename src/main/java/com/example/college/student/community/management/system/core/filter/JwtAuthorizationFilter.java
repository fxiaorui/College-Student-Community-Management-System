package com.example.college.student.community.management.system.core.filter;

import com.alibaba.fastjson.JSON;
import com.example.college.student.community.management.system.common.constants.HttpConstants;
import com.example.college.student.community.management.system.common.po.UserLoginInfoPO;
import com.example.college.student.community.management.system.common.web.JsonResult;
import com.example.college.student.community.management.system.common.web.ServiceCode;
import com.example.college.student.community.management.system.core.security.CurrentPrincipal;
import com.example.college.student.community.management.system.dao.cache.UserCacheRepository;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 处理JWT的过滤器
 */
@Slf4j
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter implements HttpConstants {

    /**
     * JWT最小长度值
     */
    public static final int JWT_MIN_LENGTH = 113;

    @Value("${background-service.jwt.secret-key}")
    private String secretKey;

    @Autowired
    private UserCacheRepository userCacheRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 尝试接收客户端的请求中携带的JWT数据
        String jwt = request.getHeader(HEADER_AUTHORIZATION);
        log.debug("客户端携带的JWT：{}", jwt);

        // 判断JWT的基本有效性（没有必要尝试解析格式明显错误的JWT数据）
        if (!StringUtils.hasText(jwt) || jwt.length() < JWT_MIN_LENGTH) {
            filterChain.doFilter(request, response);
            return;
        }

        // 从Redis中读取此JWT对应的数据
        UserLoginInfoPO loginInfo = userCacheRepository.getLoginInfo(jwt);
        // 判断Redis中是否存在此JWT相关数据
        if (loginInfo == null) {
            filterChain.doFilter(request, response);
            return;
        }
        // 判断此次请求，与当初登录成功时的相关信息是否相同
        String userAgent = loginInfo.getUserAgent();
        String ip = loginInfo.getIp();
        if (!userAgent.equals(request.getHeader(HEADER_USER_AGENT)) && !ip.equals(request.getRemoteAddr())) {
            filterChain.doFilter(request, response);
            return;
        }

        // 解析JWT数据
        response.setContentType("application/json; charset=utf-8");
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.warn("解析JWT时出现异常：ExpiredJwtException");
            String message = "操作失败，您的登录信息已经过期，请重新登录！";
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_JWT_EXPIRED, message);
            PrintWriter writer = response.getWriter();
            writer.println(JSON.toJSONString(jsonResult));
            writer.close();
            return;
        } catch (SignatureException e) {
            log.warn("解析JWT时出现异常：SignatureException");
            String message = "非法访问，你的本次操作已经被记录！";
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_JWT_SIGNATURE, message);
            PrintWriter writer = response.getWriter();
            writer.println(JSON.toJSONString(jsonResult));
            writer.close();
            return;
        } catch (MalformedJwtException e) {
            log.warn("解析JWT时出现异常：MalformedJwtException");
            String message = "非法访问，你的本次操作已经被记录！";
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_JWT_MALFORMED, message);
            PrintWriter writer = response.getWriter();
            writer.println(JSON.toJSONString(jsonResult));
            writer.close();
            return;
        } catch (Throwable e) {
            log.warn("解析JWT时出现异常: ", e);
            String message = "服务器忙，请稍后再试！";
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERROR_UNKNOWN, message);
            PrintWriter writer = response.getWriter();
            writer.println(JSON.toJSONString(jsonResult));
            writer.close();
            return;
        }

        // 从解析结果中获取用户的信息
        Long id = claims.get("id", Long.class);
        String username = claims.get("username", String.class);
        log.debug("JWT中的用户id = {}, username = {}", id, username);

        // 检查用户的启用状态
        Integer userEnable = userCacheRepository.getEnableByUserId(id);
        if (userEnable == null) {
            String message = "用户已被修改, 请重新登陆";
            log.warn(message);
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERROR_UNAUTHORIZED, message);
            PrintWriter writer = response.getWriter();
            writer.println(JSON.toJSONString(jsonResult));
            writer.close();
            return;
        }
        if (userEnable != 1) {
            String message = "用户已被禁用";
            log.warn(message);
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERROR_UNAUTHORIZED_DISABLED, message);
            PrintWriter writer = response.getWriter();
            writer.println(JSON.toJSONString(jsonResult));
            writer.close();
            return;
        }

        // 从Redis中读取当前用户的权限列表
        String authoritiesJsonString = loginInfo.getAuthoritiesJsonString();
        log.debug("从Redis中读取当前用户的权限列表 = {}", authoritiesJsonString);

        // 将解析得到的用户数据创建为Authentication对象
        CurrentPrincipal principal = new CurrentPrincipal(); // 当事人
        principal.setId(id);
        principal.setUsername(username);
        Object credentials = null; // 凭证：无需凭证
        List<SimpleGrantedAuthority> authorities
                = JSON.parseArray(authoritiesJsonString, SimpleGrantedAuthority.class);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal, credentials, authorities);

        // 将Authentication对象存入到SecurityContext中
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        // 过滤器链继续执行，即：放行
        filterChain.doFilter(request, response);
    }
}