package com.gw.web.userCenter.model.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 角色 Model
 * @auther Jeremy Zhang
 * 2019/1/7 下午1:58
 */
@ApiModel(value = "创建角色模型", description = "角色对象Role")
public class RoleCreateModel {
    @ApiModelProperty(value = "角色名称", name = "roleName", required = true, example = "project_manager")
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    @ApiModelProperty(value = "角色中文名称", name = "roleDescription", required = true, example = "系统管理员")
    @NotBlank(message = "角色中文名称不能为空")
    private String roleDescription;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }
}
