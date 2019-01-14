package com.gw.web.userCenter.model;

import java.io.Serializable;

/**
 * @program: template
 * @description:
 * @author: MengGuanghui
 * @create: 2018-12-28 13:52
 **/
public class Permission implements Serializable{

	private Integer id;

	private String permissionName;

	private String permissionCode;

	private String permissionLevel;

	private String parentId;

	private String apiUrl;

	private String apiScope;

	private String restType;

	private String remark;

	private String createDate;

	private String modifiedDate;

	private String creator;

	private String modifier;

	private String menu;

	private Boolean hasPermission;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the permissionName
	 */
	public String getPermissionName() {
		return permissionName;
	}
	/**
	 * @param permissionName the permissionName to set
	 */
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	/**
	 * @return the permissionCode
	 */
	public String getPermissionCode() {
		return permissionCode;
	}
	/**
	 * @param permissionCode the permissionCode to set
	 */
	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}
	/**
	 * @return the permissionLevel
	 */
	public String getPermissionLevel() {
		return permissionLevel;
	}
	/**
	 * @param permissionLevel the permissionLevel to set
	 */
	public void setPermissionLevel(String permissionLevel) {
		this.permissionLevel = permissionLevel;
	}
	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * @return the apiUrl
	 */
	public String getApiUrl() {
		return apiUrl;
	}
	/**
	 * @param apiUrl the apiUrl to set
	 */
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
	/**
	 * @return the apiScope
	 */
	public String getApiScope() {
		return apiScope;
	}
	/**
	 * @param apiScope the apiScope to set
	 */
	public void setApiScope(String apiScope) {
		this.apiScope = apiScope;
	}
	/**
	 * @return the restType
	 */
	public String getRestType() {
		return restType;
	}
	/**
	 * @param restType the restType to set
	 */
	public void setRestType(String restType) {
		this.restType = restType;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the modifiedDate
	 */
	public String getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}
	/**
	 * @param creator the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	/**
	 * @return the modifier
	 */
	public String getModifier() {
		return modifier;
	}
	/**
	 * @param modifier the modifier to set
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	/**
	 * @return the menu
	 */
	public String getMenu() {
		return menu;
	}
	/**
	 * @param menu the menu to set
	 */
	public void setMenu(String menu) {
		this.menu = menu;
	}

	public Boolean getHasPermission() {
		return hasPermission;
	}

	public void setHasPermission(Boolean hasPermission) {
		this.hasPermission = hasPermission;
	}
}
