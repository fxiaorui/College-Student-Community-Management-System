package com.example.college.student.community.management.system.common.web;

import com.example.college.student.community.management.system.common.ex.ServiceException;
import lombok.Data;

/**
 * 服务器端的统一响应类型
 */
@Data
public class JsonResult {

    /**
     * 响应的业务状态码值
     */
    private Integer code;

    /**
     * 操作失败时的描述文本
     */
    private String message;

    /**
     * 操作成功时的响应数据
     */
    private Object data;


    public static JsonResult ok() {
        return ok(null);
    }

    public static JsonResult ok(Object data) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(ServiceCode.OK.getValue());
        jsonResult.setData(data);
        return jsonResult;
    }

    public static JsonResult fail(ServiceException e) {
        return fail(e.getServiceCode(), e.getMessage());
    }

    public static JsonResult fail(ServiceCode serviceCode, String message) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(serviceCode.getValue());
        jsonResult.setMessage(message);
        return jsonResult;
    }

}
