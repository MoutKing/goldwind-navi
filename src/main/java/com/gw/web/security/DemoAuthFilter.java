package com.gw.web.security;

import com.alibaba.druid.util.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: template
 * @description:
 * @author: MengGuanghui
 * @create: 2019-01-04 19:17
 **/
public class DemoAuthFilter extends GenericFilterBean {

    private final AuthenticationManager authenticationManager;

    public DemoAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String token = httpServletRequest.getHeader("app_token");
        if(StringUtils.isEmpty(token)){
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "invalid token");
            return ;
        }

        try {
            Authentication auth = authenticationManager.authenticate(new WebToken(token));
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (AuthenticationException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }
}