package com.gw.web.common.utils;

import com.gw.web.common.base.rest.RestResponse;
import com.gw.web.common.model.enums.Errors;
import org.springframework.util.ObjectUtils;

/**
 * RestAPI Utils
 * @auther Jeremy Zhang
 * 2019/1/7 下午5:16
 */
public class RestUtil {

    public static <T> RestResponse convertResponse(T result) {
        return convertResponse(Errors.SUCCESS.getErrorCode(), Errors.SUCCESS.getErrorMessage(), result);
    }

    public static <T> RestResponse convertResponse(int code, String message, T result){
        RestResponse resultInfo = new RestResponse();
        resultInfo.setCode(code);
        resultInfo.setMessage(message);
        resultInfo.setData(result);
        return resultInfo;
    }

    public static RestResponse convertResponse() {
        return convertResponse(null);
    }
}
