package com.gw.web.common.base.rest;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.omg.CORBA.TIMEOUT;

import java.util.HashMap;

/**
 * @program: template
 * @description:
 * @author: MengGuanghui
 * @create: 2019-01-02 10:34
 **/
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@ApiModel(value="Resp",description="模型管理前后台交互对象")
public class RestResponse<T> {
    public static final int OK = 0;
    public static final int UNKNOWN_ERROR = -1;
    public static final int RESPONSE_CODE_WARNING = 2;
    public static final int SUCCESS = 1000;     // 服务端请求成功
    public static final int SYSTEM_ERROR = 2001;    // 系统未知错误
    public static final int PARAMETER_MISSING = 3009;   // 缺少必要的参数
    public static final int METHOD_INVALID = 3010;  // 请求方法不正确
    public static final int NODE_ERROR = 3015;      // 请求节点不正确
    public static final int PARAM_IS_TOO_LONG = 3023;   // 请求参数过长
    public static final int METHOD_IS_NULL = 3025;   // 请求方法不正确(不能为空)
    public static final int PARAM_IS_NULL = 3026;   // 请求参数为空(不能为空)
    public static final int PARAM_ERROR = 3027;   // 请求参数错误
    public static final int TIME_STAMP_TIMEOUT = 3071;   // 时间戳不在有效范围内
    public static final int SIGN_VALUE_IS_DIFFERENT = 3074;   // 签名不一致
    public static final int SIGN_CHECK_PARAM_ERROR = 3075;   // 签名认证模块认证参数失败
    public static final int TOKEN_ILLEGAL = 3083;   // token非法
    public static final int LOGIN_TOKEN_ILLEGAL = 3084;   // 登录的Token非法
    public static final int DECRYPT_REQUEST_ERROR = 3097;   // 解密请求失败
    public static final int SN_IS_NULL = 3099;   // SN为空
    public static final int INVALID_ACCOUNT = 3201;   // 非法账号
    public static final int PARAMS_IS_TOO_LONG = 3202;   // 参数过长
    public static final int NO_ACCESS_PERMISSION = 3602;   // 没有访问权限



    @ApiModelProperty(value = "返回code，正常为0，异常为异常code编码",name="code",example="-1")
    private int code = -1;
    @ApiModelProperty(value="统一提示语",name="msg",example="成功时一般为空串，失败时为统一提示")
    private String message = "";  	 		// 统一提示语
    @ApiModelProperty(value="交互数据，可以是任何类型，按具体需要定义",name="data",example="")
    private T data=null; 			// 封装数据

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
