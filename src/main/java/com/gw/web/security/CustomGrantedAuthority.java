package com.gw.web.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * @program: template
 * @description:
 * @author: MengGuanghui
 * @create: 2018-12-28 15:18
 **/
public class CustomGrantedAuthority implements GrantedAuthority {

    private String permissionUrl;
    private String method;

    public String getPermissionUrl() {
        return permissionUrl;
    }

    public void setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public CustomGrantedAuthority(String permissionUrl, String method) {
        this.permissionUrl = permissionUrl;
        this.method = method;
    }

    @Override
    public String getAuthority() {
        return this.permissionUrl + ";"+this.method;
    }
}

