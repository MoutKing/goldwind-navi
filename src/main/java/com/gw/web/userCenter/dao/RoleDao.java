package com.gw.web.userCenter.dao;

import com.gw.web.userCenter.model.Role;

import java.util.List;
import java.util.Map;

/**
 * 角色 Dao
 * @auther Jeremy Zhang
 * 2019/1/7 下午5:16
 */
public interface RoleDao {

    List<Role> getByMap(Map<String, Object> map);

    Role getById(Integer id);

    Integer create(Role role);

    int update(Role role);

    Role getByRoleName(String roleName);

    List<Role> getByUserId(Integer userId);

    Integer getRelationCountOfUserRole(Integer roleId);

    Integer delete(Integer roleId);
}
