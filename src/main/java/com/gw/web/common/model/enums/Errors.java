package com.gw.web.common.model.enums;

/**
 * 错误类型
 * @auther Jeremy Zhang
 * 2019/1/7 下午12:17
 */
public enum Errors {

    SUCCESS(1000, "操作成功"),
    SYSTEM_ERROR(2001, "系统未知错误"),
    PARAMETER_MISSING(3009, "缺少必要的参数"),
    METHOD_INVALID(3010, "请求方法不正确"),
    PARAM_IS_TOO_LONG(3023,"请求参数过长"),
    PARAM_NOT_FOUND(3024,"请求数据不存在"),
    METHOD_IS_NULL(3025,"请求方法不正确"),
    PARAM_IS_NULL(3026,"请求参数为空"),
    PARAM_ERROR(3027,"请求参数错误"),
    OBJECT_IS_NULL(4001, "操作对象不存在"),
    OBJECT_IS_CHANGED(4002, "操作对象状态已改变"),
    OBJECT_IS_IMMUTABLE(4003, "操作对象不可修改"),
    OBJECT_IS_DELETED(4004, "操作对象已删除"),
    SYSTEM_BUSY(9999,"系统繁忙，请稍后再试");

    private int errorCode;
    private String errorMessage;

    Errors(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }



}
