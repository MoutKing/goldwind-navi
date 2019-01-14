package com.gw.web.userCenter.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.web.common.exception.RestException;
import com.gw.web.common.model.enums.Errors;
import com.gw.web.userCenter.dao.PermissionDao;
import com.gw.web.userCenter.dao.RoleDao;
import com.gw.web.userCenter.model.Permission;
import com.gw.web.userCenter.model.Role;
import com.gw.web.userCenter.model.RolePermission;
import com.gw.web.userCenter.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;

/**
 * 角色 Service Implement
 * @auther Jeremy
 * 2019/1/7 下午1:43
 */
@Service
public class RoleServiceImpl implements RoleService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public void createRole(Role role) {

        // Determine if the same roles exist
        if( !ObjectUtils.isEmpty( roleDao.getByRoleName(role.getRoleName()) )){
            throw new RestException(Errors.PARAM_ERROR, "角色名重复");
        }

        // Create new role
        logger.info("Create new role: " + role.getRoleName());
        int result = roleDao.create(role);
        if(result != 1) {
            throw new RestException(Errors.SYSTEM_ERROR, "创建角色失败");
        }

    }

    @Override
    public PageInfo<Role> findRoleList(Page page) {
        PageHelper.startPage(page);
        logger.info("Query roles page, pageSize: " + page.getPageSize() + ", pageNum: " + page.getPageNum());

        List<Role> roleList = roleDao.getByMap(new HashMap<>());
        PageInfo<Role> pageInfo = new PageInfo<>(roleList);
        logger.info("Query roles result, size: " + pageInfo.getSize() + ", total: " + page.getTotal());

        return pageInfo;
    }

    @Override
    public void deleteRole(Integer roleId) {
        // Determine if the role exist
        if( ObjectUtils.isEmpty( roleDao.getById(roleId) ))
            throw new RestException(Errors.OBJECT_IS_NULL);

        // Determine if the role has relations with user
        int count = roleDao.getRelationCountOfUserRole(roleId);
        if (count > 0)
            throw new RestException(Errors.OBJECT_IS_IMMUTABLE, "当前角色不可删除");

        try {
            // Delete the relations with permissions of this role
            List<RolePermission> rolePermissions = permissionDao.getRelationOfRolePermission(roleId);
            for(RolePermission rolePermission : rolePermissions) {
                logger.info("Delete the relation to permission of the role, roleId: " + rolePermission.getRoleId() + ", permissionId: " + rolePermission.getPermissionId());
                permissionDao.deleteRelationOfRolePermission(roleId, rolePermission.getPermissionId());
            }
            // Delete this role
            logger.info("Delete this role, roleId: " + roleId );
            roleDao.delete(roleId);
        } catch (Exception e) {
            logger.error("Deletion failed, roleId: " + roleId, e);
            throw new RestException(Errors.SYSTEM_BUSY);
        }
    }
}
