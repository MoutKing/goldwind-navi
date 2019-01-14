package com.gw.web.userCenter.dao;

import com.gw.web.userCenter.model.Permission;
import com.gw.web.userCenter.model.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @program: template
 * @description:
 * @author: MengGuanghui
 * @create: 2018-12-28 14:00
 **/
public interface PermissionDao {

//    List<Permission> getByMap(Map<String, Object> map);
//
//    Permission getById(Integer id);

    Integer addPermission(Permission permission);

//    int update(Permission permission);

    List<Permission> getByUserId(Integer userId);

    List<Permission> getByRoleId(Integer roleId);
    
    List<Permission> getPermissionTree();

    List<RolePermission> getRelationOfRolePermission(Integer roleId);

    Integer addRelationOfRolePermission(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);

    Integer deleteRelationOfRolePermission(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);
}
