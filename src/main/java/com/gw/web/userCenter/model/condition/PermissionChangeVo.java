package com.gw.web.userCenter.model.condition;

import java.util.List;

/**
 * 角色修改权限
 * @auther Jeremy Zhang
 * 2019/1/8 下午4:58
 */
public class PermissionChangeVo {

    private String roleId;

    private List<String> permissionIds;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<String> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<String> permissionIds) {
        this.permissionIds = permissionIds;
    }
}
