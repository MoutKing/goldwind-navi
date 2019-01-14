package com.gw.web.common.exception;

import com.gw.web.common.model.enums.Errors;

/**
 * 内部异常 500
 * @auther Jeremy Zhang
 * 2018/10/16 下午10:38
 */
public class RestException extends RuntimeException {

    private int code;

    private String message;

    private Object data;

    public RestException(Errors errors){
        super(errors.getErrorMessage());
        this.code = errors.getErrorCode();
        this.message = errors.getErrorMessage();
    }

    public RestException(Errors errors, String errorMsg){
        super(errors.getErrorMessage());
        this.code = errors.getErrorCode();
        this.message = errorMsg;
    }

    public RestException(Errors errors, String errorMsg, Object data) {
        super(errors.getErrorMessage());
        this.data = data;
        this.code = errors.getErrorCode();
        this.message = errorMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
