package com.example.college.student.community.management.system.core.handler;

import com.example.college.student.community.management.system.common.ex.ServiceException;
import com.example.college.student.community.management.system.common.web.JsonResult;
import com.example.college.student.community.management.system.common.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public JsonResult handleServiceException(ServiceException e) {
        log.debug("全局异常处理器开始处理ServiceException");
        return JsonResult.fail(e);
    }

    @ExceptionHandler
    public JsonResult handleBindException(BindException e) {
        log.debug("全局异常处理器开始处理BindException");
        // 以下代码将提示所有格式错误的文本
        // StringJoiner stringJoiner = new StringJoiner("，", "请求参数格式错误，", "！");
        // List<FieldError> fieldErrors = e.getFieldErrors();
        // for (FieldError fieldError : fieldErrors) {
        //     String defaultMessage = fieldError.getDefaultMessage();
        //     stringJoiner.add(defaultMessage);
        // }
        // String message = stringJoiner.toString();
        // log.warn(message);
        // return JsonResult.fail(ServiceCode.ERROR_BAD_REQUEST, message);
        // 以下代码将提示随机的某个格式错误的文本
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("请求参数格式错误，");
        stringBuilder.append(e.getFieldError().getDefaultMessage());
        stringBuilder.append("！");
        String message = stringBuilder.toString();
        log.warn(message);
        return JsonResult.fail(ServiceCode.ERROR_BAD_REQUEST, message);
    }

    @ExceptionHandler
    public JsonResult handleConstraintViolationException(ConstraintViolationException e) {
        log.debug("全局异常处理器开始处理ConstraintViolationException");
        StringBuilder stringBuilder = new StringBuilder();
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            stringBuilder.append(constraintViolation.getMessage());
        }
        String message = stringBuilder.toString();
        return JsonResult.fail(ServiceCode.ERROR_BAD_REQUEST, message);
    }

    @ExceptionHandler({
            InternalAuthenticationServiceException.class,
            BadCredentialsException.class
    })
    public JsonResult handleAuthenticationException(AuthenticationException e) {
        log.debug("捕获了AuthenticationException");
        log.debug("异常类型：{}", e.getClass().getName());
        String message = "登录失败，用户名或密码错误！";
        return JsonResult.fail(ServiceCode.ERROR_UNAUTHORIZED, message);
    }

    @ExceptionHandler
    public JsonResult handleDisabledException(DisabledException e) {
        log.debug("捕获了DisabledException", e);
        String message = "登录失败，此账号已经被禁用！";
        return JsonResult.fail(ServiceCode.ERROR_UNAUTHORIZED_DISABLED, message);
    }

    @ExceptionHandler
    public JsonResult handleAccessDeniedException(AccessDeniedException e) {
        log.debug("捕获了AccessDeniedException", e);
        String message = "操作失败，当前登录的账号无此操作权限！";
        return JsonResult.fail(ServiceCode.ERROR_FORBIDDEN, message);
    }

    @ExceptionHandler
    public JsonResult handleThrowable(Throwable e) {
        log.debug("捕获了Throwable", e);
        String message = "服务器忙，请稍后再试！";
        return JsonResult.fail(ServiceCode.ERROR_UNKNOWN, message);
    }

}
