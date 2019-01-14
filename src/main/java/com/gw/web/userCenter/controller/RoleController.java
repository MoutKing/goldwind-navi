package com.gw.web.userCenter.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.gw.web.common.base.rest.RestResponse;
import com.gw.web.common.utils.RestUtil;
import com.gw.web.userCenter.model.Role;
import com.gw.web.userCenter.model.condition.RoleCreateModel;
import com.gw.web.userCenter.service.RoleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Role Controller
 * @auther Jeremy Zhang
 * 2019/1/7 下午12:17
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "创建角色", notes = "通过角色名,创建角色")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "正常"),
            @ApiResponse(code = 500, message = "服务器内部错误")})
    @PostMapping
    public RestResponse createRole(@Valid @RequestBody RoleCreateModel model) {

        // 调用role service
        Role role = new Role();
        role.setRoleName(model.getRoleName());
        role.setRoleDescription(model.getRoleDescription());
        roleService.createRole(role);

        return RestUtil.convertResponse();
    }

    @ApiOperation(value = "查询角色列表", notes = "查询所有角色")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "正常"),
            @ApiResponse(code = 500, message = "服务器内部错误")})
    @PostMapping("/list")
    public RestResponse getRoleList(Page page) {

        PageInfo<Role> roles = roleService.findRoleList(page);

        return RestUtil.convertResponse(roles);
    }

    @ApiOperation(value = "删除角色", notes = "删除选择的角色")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "正常"),
            @ApiResponse(code = 400, message = "请求参数错误"),
            @ApiResponse(code = 500, message = "服务器内部错误")})
    @DeleteMapping("/{roleId:\\d+}")
    public RestResponse deleteRole(@PathVariable Integer roleId) {
        roleService.deleteRole(roleId);
        return RestUtil.convertResponse();
    }

}
