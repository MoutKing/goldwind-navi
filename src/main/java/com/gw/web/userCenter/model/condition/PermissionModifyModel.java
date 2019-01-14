package com.gw.web.userCenter.model.condition;

import com.gw.web.userCenter.model.Permission;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 角色权限修改 Model
 * @auther Jeremy Zhang
 * 2019/1/7 下午1:58
 */
@ApiModel(value = "角色权限修改模型", description = "修改角色权限关系")
public class PermissionModifyModel {
    @ApiModelProperty(value = "角色ID", name = "roleId", required = true, example = "112")
    @NotBlank(message = "角色ID不能为空")
    private Integer roleId;

    @ApiModelProperty(value = "权限集合", name = "permissions", required = true, example = "[{id: 221}]")
    @NotEmpty(message = "权限集合不能为空")
    private List<Permission> permissions;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
