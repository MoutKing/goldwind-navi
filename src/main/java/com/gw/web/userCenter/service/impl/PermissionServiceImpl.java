package com.gw.web.userCenter.service.impl;

import com.gw.web.common.exception.BadRequestException;
import com.gw.web.common.exception.RestException;
import com.gw.web.common.model.enums.Errors;
import com.gw.web.userCenter.dao.RoleDao;
import com.gw.web.userCenter.model.Permission;
import com.gw.web.userCenter.model.Role;
import com.gw.web.userCenter.model.RolePermission;
import com.gw.web.userCenter.service.PermissionService;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.gw.web.common.utils.YamlUtils;
import com.gw.web.userCenter.dao.PermissionDao;
import org.springframework.util.ObjectUtils;

/**
 * @program: template
 * @description:
 * @author: MengGuanghui
 * @create: 2018-12-28 14:59
 **/
@Service
public class PermissionServiceImpl implements PermissionService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PermissionDao permissionDao;
	@Autowired
	private RoleDao roleDao;

	@Override
	public Integer addPermission(Permission permission) throws Exception {
		int returnValue = permissionDao.addPermission(permission);
		return returnValue;
	}

	@Override
	public Integer addPermissionListByYAML(String fileName) throws Exception {
		int successNum = 0;
		List<Permission> pList = YamlUtils.getPermissionList(fileName);
		for (int k = 0; k < pList.size(); k++) {
			permissionDao.addPermission(pList.get(k));
			successNum++;
		}
		return successNum;
	}

	@Override
	public JSONObject getPermissionTree(Integer roleId) {
        // Determine if the role exist
        if( ObjectUtils.isEmpty( roleDao.getById(roleId) ))
            throw new RestException(Errors.OBJECT_IS_NULL);

        List<Permission> permissionTreeList = null;
        List<RolePermission> rolePermissions = null;
        try {
            // 从数据库中加载权限列表
            logger.info("Find the tree of permissions");
            permissionTreeList = permissionDao.getPermissionTree();
            logger.info("Find all permissions of the role, roleId: " + roleId);
            rolePermissions = permissionDao.getRelationOfRolePermission(roleId);
        } catch (Exception e) {
            logger.error("Lookup permissions tree failed, roleId: " + roleId, e);
            throw new RestException(Errors.SYSTEM_BUSY);
        }

        Set<Integer> permissionIds = new HashSet<>();
		for(RolePermission rolePermission : rolePermissions) {
            permissionIds.add(rolePermission.getPermissionId());
        }

		JSONObject jsonObjResult = new JSONObject();
		for (int i = 0; i < permissionTreeList.size(); i++) {// 遍历列表
			Permission p = permissionTreeList.get(i);
            if (permissionIds.contains(p.getId()))
                p.setHasPermission(true);

			if (Integer.valueOf(p.getParentId()) == 0) {// 取出顶级元素,permissionLevel=1
				jsonObjResult.put(String.valueOf(p.getId()), p);
			} else {// 递归取得权限树结构
				JSONObject jsonTemp = new JSONObject();
				jsonTemp.put(String.valueOf(p.getId()), p);
				getTree(jsonObjResult, jsonTemp.getJSONObject(String.valueOf(p.getId())));
			}
		}
		return jsonObjResult;
	}

	public JSONObject getTree(JSONObject resultJson, JSONObject insertNode) {// param1为最终得出的树结构,param2为要插入的节点
		// 获得将要插入树的节点的父节点-parentId
		String parentId = insertNode.get("parentId").toString();
		// 将JSONObject转成Map
		LinkedHashMap<String, String> jsonMap = JSON.parseObject(resultJson.toJSONString(),
				new TypeReference<LinkedHashMap<String, String>>() {
				});
		for (Map.Entry<String, String> entry : jsonMap.entrySet()) {// entry.getKey()
			if (entry.getKey().equals(parentId)) {// entry.getKey()为当前JSONOject的键名
				if (resultJson.getJSONObject(parentId).containsKey("children")) {
					JSONObject insertNodeReceiver = resultJson.getJSONObject(entry.getKey()).getJSONObject("children");
					insertNodeReceiver.put(String.valueOf(insertNode.get("id")), insertNode);
				} else {// 若当前父节点没有子节点，则新建子节点
					JSONObject jsonTemp = new JSONObject();
					jsonTemp.put(String.valueOf(insertNode.get("id")), insertNode);
					JSONObject insertNodeReceiver = resultJson.getJSONObject(entry.getKey());
					insertNodeReceiver.put("children", jsonTemp);
					resultJson.replace(entry.getKey(), insertNodeReceiver);
				}
			} else {// 未找到匹配的父节点则继续在此循环内迭代
				if (resultJson.getJSONObject(entry.getKey()).containsKey("children")) {
					getTree(resultJson.getJSONObject(entry.getKey()).getJSONObject("children"), insertNode);
				}
			}
		}
		return resultJson;
	}

    public List<Permission> findPermissionByRole(Integer roleId) {
		logger.info("Find all Permission of the role, roleId: " + roleId);
		List<Permission> permissions = permissionDao.getByRoleId(roleId);
        return permissions;
    }

    @Override
    public void updatePermissionOfRole(Integer roleId, List<Permission> permissions) {
        // Check params
	    if(roleId == null || roleId < 1 || ObjectUtils.isEmpty(permissions))
            throw new BadRequestException(Errors.PARAM_IS_NULL);

	    logger.info("the relation to permission of the role, roleId: " + roleId);
        // Determine if the role exist
        Role role = roleDao.getById(roleId);
        if (ObjectUtils.isEmpty(role))
            throw new RestException(Errors.OBJECT_IS_NULL);

        Set<Integer> oldPermissionIds = new HashSet<>();
        Set<Integer> newPermissionIds = new HashSet<>();
        Set<Integer> deleteIds = new HashSet<>();

        // Query old permissions of the role
        List<RolePermission> rolePermissions = permissionDao.getRelationOfRolePermission(roleId);
        for (RolePermission rolePermission : rolePermissions) {
            oldPermissionIds.add(rolePermission.getPermissionId());
        }

        for (Permission permission : permissions) {
            newPermissionIds.add(permission.getId());
        }
        // If permissionId both in two set, don't change
        for(Integer id : oldPermissionIds) {
            if (newPermissionIds.contains(id)) {
                newPermissionIds.remove(id);
            } else {
                deleteIds.add(id);
            }
        }
        // Finally add remaining ids in newIds, remove ids in deleteIds
		try {
			for (Integer permissionId : newPermissionIds) {
				logger.info("Add permission of the role, permissionId: " + permissionId);
				permissionDao.addRelationOfRolePermission(roleId, permissionId);
			}

			for (Integer permissionId : deleteIds) {
				logger.info("Delete permission of the role, permissionId: " + permissionId);
				permissionDao.deleteRelationOfRolePermission(roleId, permissionId);
			}
		} catch (Exception e) {
			logger.error("Update failed, roleId: " + roleId, e);
			throw new RestException(Errors.SYSTEM_BUSY);
		}

	}
}
