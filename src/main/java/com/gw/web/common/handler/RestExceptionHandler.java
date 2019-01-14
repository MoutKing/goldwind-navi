package com.gw.web.common.handler;

import com.gw.web.common.base.rest.RestResponse;
import com.gw.web.common.exception.BadRequestException;
import com.gw.web.common.exception.ForbiddenException;
import com.gw.web.common.exception.NotFoundException;
import com.gw.web.common.exception.RestException;
import com.gw.web.common.utils.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception处理器
 * @auther Jeremy Zhang
 * 2018/10/16 下午10:46
 */
@ControllerAdvice
public class RestExceptionHandler {
    Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(ForbiddenException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public RestResponse handleNotFoundException(ForbiddenException ex){
        return this.convertResponse(ex);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse handleNotFoundException(BadRequestException ex){
        return this.convertResponse(ex);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RestResponse handleNotFoundException(NotFoundException ex){
        return this.convertResponse(ex);
    }

    @ExceptionHandler(RestException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResponse handleRestException(RestException ex){
        return this.convertResponse(ex);
    }

    private RestResponse convertResponse(RestException ex){
        logger.error("系统异常: " + ex.getCode(), ex);
        return RestUtil.convertResponse(ex.getCode(), ex.getMessage(), ex.getData());
    }

}
