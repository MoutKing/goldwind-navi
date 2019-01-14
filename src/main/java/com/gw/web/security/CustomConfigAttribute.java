package com.gw.web.security;

import org.springframework.security.access.ConfigAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: template
 * @description:
 * @author: MengGuanghui
 * @create: 2018-12-28 15:30
 **/
public class CustomConfigAttribute implements ConfigAttribute {

    private final HttpServletRequest httpServletRequest;

    public CustomConfigAttribute(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }


    @Override
    public String getAttribute() {
        return null;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }
}
