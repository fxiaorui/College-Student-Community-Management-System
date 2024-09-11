package com.example.college.student.community.management.system.common.ex;

import com.example.college.student.community.management.system.common.web.ServiceCode;
import lombok.Getter;

/**
 * 业务异常
 */
public class ServiceException extends RuntimeException {

    @Getter
    private final ServiceCode serviceCode;


    public ServiceException(ServiceCode serviceCode, String message) {
        super(message);
        this.serviceCode = serviceCode;
    }

}
