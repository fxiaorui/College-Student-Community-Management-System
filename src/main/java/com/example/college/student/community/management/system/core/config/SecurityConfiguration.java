package com.example.college.student.community.management.system.core.config;


import com.alibaba.fastjson.JSON;
import com.example.college.student.community.management.system.common.web.JsonResult;
import com.example.college.student.community.management.system.common.web.ServiceCode;
import com.example.college.student.community.management.system.core.filter.JwtAuthorizationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;

/**
 * Spring Security的配置类
 */
@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启基于方法的安全检查
// @EnableWebSecurity(debug = true) // 开启调试模式，在控制台将显示很多日志，在生产环境中不宜开启
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;


    @Bean
    public PasswordEncoder passwordEncoder() {
        // return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 配置Security框架不使用Session
        // SessionCreationPolicy.NEVER：从不主动创建Session，但是，Session存在的话，会自动使用
        // SessionCreationPolicy.STATELESS：无状态，无论是否存在Session，都不使用
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 将自定义的解析JWT的过滤器添加到Security框架的过滤器链中
        // 必须添加在检查SecurityContext的Authentication之前，具体位置并不严格要求
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        // 允许跨域访问，本质上是启用了Security框架自带的CorsFilter
        // 如果不启用CorsFilter，也可以改为对所有OPTIONS请求直接许可，一样可以解决复杂请求预检的跨域问题
        // 注意：即使此处许可以复杂请求的预检，Spring MVC配置类中的启用跨域的配置仍是必须的
        http.cors();

        // 处理“无认证信息却访问需要认证的资源时”的响应
        http.exceptionHandling().authenticationEntryPoint((request, response, e) -> {
            log.warn("", e);
            response.setContentType("application/json; charset=utf-8");
            String message = "操作失败，您当前未登录！";
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERROR_UNAUTHORIZED, message);
            PrintWriter writer = response.getWriter();
            writer.println(JSON.toJSONString(jsonResult));
            writer.close();
        });

        // 白名单
        String[] urls = {
                "/favicon.ico",
                "/doc.html",
                "/**/*.css",
                "/**/*.js",
                "/swagger-resources",
                "/v2/api-docs",
                "/resources/**", // 静态资源文件夹，通常是上传的文件，请与配置文件中的"upload.base-dir-name"一致
                "/users/login", // 用户登录
                "/users/reg", // 用户注册
                "/1.jpg"
        };

        // 禁用“防止伪造的跨域攻击的防御机制”
        http.csrf().disable();

        // 配置请求授权
        // 如果某个请求被多次配置，按照“第一匹配原则”处理
        // 应该将精确的配置写在前面，将较模糊的配置写在后面
        http.authorizeRequests()
                // .mvcMatchers(HttpMethod.OPTIONS, "/**") // 匹配所有OPTIONS类型的请求
                // .permitAll() // 许可
                .mvcMatchers(urls) // 匹配某些请求
                .permitAll() // 许可，即不需要通过认证就可以访问
                .anyRequest() // 任何请求，从执行效果来看，也可以视为：除了以上配置过的以外的其它请求
                .authenticated(); // 需要通过认证才可以访问

        // 是否调用以下方法，将决定是否启用默认的登录页面
        // 当未通过认证时，如果有登录页，则自动跳转到登录，如果没有登录页，则直接响应403
        // http.formLogin();
    }

}