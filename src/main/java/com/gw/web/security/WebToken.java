package com.gw.web.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * @program: template
 * @description:
 * @author: MengGuanghui
 * @create: 2019-01-04 19:20
 **/
public class WebToken extends AbstractAuthenticationToken {

    private final String token;

    public WebToken(String token) {
        super(null);
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
