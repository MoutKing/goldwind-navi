package com.gw.web.userCenter.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.gw.web.userCenter.model.Role;

import java.util.List;

/**
 * 角色 Service
 * @auther Jeremy Zhang
 * 2019/1/7 下午5:16
 */
public interface RoleService {
    /**
     * 新建角色
     * 2019/01/07 14:04:11
     * @author Jeremy Zhang
     */
    void createRole(Role role);
    /**
     * 查询角色列表
     * 2019/01/07 14:04:11
     * @author Jeremy Zhang
     */
    PageInfo<Role> findRoleList(Page page);
    /**
     * 查询角色列表
     * 2019/01/07 14:04:11
     * @author Jeremy Zhang
     */
    void deleteRole(Integer roleId);
}
