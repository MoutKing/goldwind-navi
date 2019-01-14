package com.gw.web.userCenter.controller;

import com.gw.web.common.utils.RestUtil;
import com.gw.web.userCenter.model.condition.PermissionModifyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.gw.web.common.base.rest.RestResponse;
import com.gw.web.userCenter.model.Permission;
import com.gw.web.userCenter.service.PermissionService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.validation.Valid;

/**
 * @program: template
 * @description:
 * @author: LiSong
 * @create: 2019-01-09 13:42
 **/
@RestController
@RequestMapping("/permission")
public class PermissionController {
	Logger logger = LoggerFactory.getLogger(PermissionController.class);

	@Autowired
	private PermissionService permissionService;

	@ApiOperation(value = "新增功能权限", notes = "新增功能权限")
	@ApiResponses(value = { @ApiResponse(code = 0, message = "正常"), @ApiResponse(code = 3027, message = "请求参数错误"),
			@ApiResponse(code = 2002, message = "有源异常") })
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse<Object> addPermission(@RequestBody JSONObject jsonObject) {
		RestResponse<Object> restResponse = new RestResponse<Object>();
		try {
			if(jsonObject.equals(null)) {
				restResponse.setCode(RestResponse.PARAM_ERROR);
				restResponse.setData("");
				restResponse.setMessage("参数异常");
			} else {
				Permission permission = jsonObject.toJavaObject(Permission.class);
				int returnFlag = permissionService.addPermission(permission);
				restResponse.setCode(RestResponse.OK);
				restResponse.setData("");
				restResponse.setMessage(returnFlag >0 ? "新增权限成功" : "未能新增");
			}
		} catch (Exception e) {
			restResponse.setCode(RestResponse.UNKNOWN_ERROR);
			restResponse.setData("");
			restResponse.setMessage(e.getMessage());
		}
		return restResponse;
	}

	@ApiOperation(value = "通过加载yml文件新增权限", notes = "通过加载yml文件新增权限")
	@ApiResponses(value = { @ApiResponse(code = 0, message = "正常"), @ApiResponse(code = 3027, message = "请求参数错误"),
			@ApiResponse(code = 2002, message = "有源异常") })
	@RequestMapping(value = "/addlistbyyml", method = RequestMethod.POST)
	public RestResponse<Object> addPermissionListFromYAML(@RequestBody JSONObject jsonObject) {
		RestResponse<Object> restResponse = new RestResponse<Object>();
		try {
			if(jsonObject.equals(null)) {
				restResponse.setCode(RestResponse.PARAM_ERROR);
				restResponse.setData("");
				restResponse.setMessage("参数异常");
			} else {
				String fileName = jsonObject.get("fileName").toString();
				int returnFlag = permissionService.addPermissionListByYAML(fileName);
				restResponse.setCode(RestResponse.OK);
				restResponse.setData("");
				restResponse.setMessage(returnFlag >0 ? "新增权限成功" : "未能新增");
			}
		} catch (Exception e) {
			restResponse.setCode(RestResponse.UNKNOWN_ERROR);
			restResponse.setData("");
			restResponse.setMessage(e.getMessage());
		}
		return restResponse;
	}

	@ApiOperation(value = "获得树结构", notes = "通过加载yml文件新增权限")
	@ApiResponses(value = { @ApiResponse(code = 0, message = "正常"), @ApiResponse(code = 3027, message = "请求参数错误"),
			@ApiResponse(code = 2002, message = "有源异常") })
	@GetMapping(value = "/tree/{roleId:\\d+}")
	public RestResponse getPermissionTree(@PathVariable Integer roleId) {

		JSONObject returnString = permissionService.getPermissionTree(roleId);

		return RestUtil.convertResponse(returnString);
	}

	@ApiOperation(value = "角色下拥有权限修改", notes = "新增或修改选择角色与权限对应关系")
    @ApiResponses(value = { @ApiResponse(code = 0, message = "正常"),
            @ApiResponse(code = 400, message = "请求参数错误"),
            @ApiResponse(code = 500, message = "服务器内部错误") })
	@PostMapping("/modifyRole")
	public RestResponse modifyRolePermissions(@Valid @RequestBody PermissionModifyModel model) {

		permissionService.updatePermissionOfRole(model.getRoleId(), model.getPermissions());

		return RestUtil.convertResponse();
	}
}
