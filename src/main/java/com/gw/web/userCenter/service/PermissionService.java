package com.gw.web.userCenter.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.gw.web.userCenter.model.Permission;

public interface PermissionService {

	public Integer addPermission(Permission permission) throws Exception;

	public Integer addPermissionListByYAML(String filePath) throws Exception;

	public JSONObject getPermissionTree(Integer roleId);
	
	/**
	 * 查询角色下所有权限 
	 * 2019/01/08 17:07:50
	 * @author Jeremy Zhang
	 */
	List<Permission> findPermissionByRole(Integer roleId);
	/**
	 * 修改角色所有权限
	 * 2019/01/10 15:16:59
	 * @author Jeremy Zhang
	 */
	void updatePermissionOfRole(Integer roleId, List<Permission> permissions);
}
