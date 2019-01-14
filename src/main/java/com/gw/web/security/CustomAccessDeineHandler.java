package com.gw.web.security;

import com.alibaba.fastjson.JSONObject;
import com.gw.web.common.base.rest.RestResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: template
 * @description:
 * @author: MengGuanghui
 * @create: 2019-01-02 10:13
 **/
public class CustomAccessDeineHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/javascript;charset=utf-8");
        RestResponse<Object> result = new RestResponse<Object>();
        result.setCode(RestResponse.NO_ACCESS_PERMISSION);
        result.setMessage("没有访问权限2!");
        response.getWriter().print(JSONObject.toJSONString(result));
    }

}
