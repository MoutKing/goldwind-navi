package com.gw.web.userCenter.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.gw.web.common.base.pagination.PageResult;
import com.gw.web.common.base.rest.RestResponse;
import com.gw.web.common.model.Page;
import com.gw.web.userCenter.model.User;
import com.gw.web.userCenter.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @program: template
 * @description:
 * @author: MengGuanghui
 * @create: 2019-01-04 13:42
 **/
@RestController
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@ApiOperation(value = "用户列表分页查询", notes = "查看所有账号信息")
	@ApiResponses(value = { @ApiResponse(code = 0, message = "正常"), @ApiResponse(code = 3027, message = "请求参数错误"),
			@ApiResponse(code = 2002, message = "有源异常") })
//	@ApiImplicitParams({@ApiImplicitParam(name="page", value="页码",required=false,dataType="Integer"),
//						@ApiImplicitParam(name="size", value="每页大小",required=false,dataType="Integer")})
	@RequestMapping(value = "/user/getuserlistbyrole/{roleId}/{page}/{size}", method = RequestMethod.GET)
	public RestResponse<Object> queryUserList(@PathVariable("roleId") Integer roleId,
			@PathVariable(value="page", required=false) Integer pageNo,
			@PathVariable(value="size", required=false) Integer pageSize) {
		RestResponse<Object> restResponse = new RestResponse<Object>();
		try {
			if (roleId > 0) {
				PageInfo<User> pList = null;
				pList = userService.getUserListByRole(roleId, pageNo, pageSize);
				restResponse.setCode(RestResponse.OK);
				restResponse.setData(pList);
				restResponse.setMessage("");
			} else {
				restResponse.setCode(RestResponse.PARAM_ERROR);
				restResponse.setData("");
				restResponse.setMessage("请求参数错误");
			}
		} catch (Exception e) {
			restResponse.setCode(RestResponse.UNKNOWN_ERROR);
			restResponse.setData("");
			restResponse.setMessage(e.getMessage());
		}
		return restResponse;
	}
	
	@ApiOperation(value = "根据条件查询特定用户", notes = "查看特定账号信息")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "正常"),
            @ApiResponse(code = 3027, message = "请求参数错误"),
            @ApiResponse(code = 2002, message = "有源异常")})
    @RequestMapping(value = "/user/getuserbycondition/{roleId}/{condition}/{page}/{size}", method = RequestMethod.GET)
    public RestResponse<Object> queryUserByCondition(@PathVariable("roleId") Integer roleId,
    		@PathVariable("condition") String condition,
    		@PathVariable(value="page", required=false) Integer pageNo,
			@PathVariable(value="size", required=false) Integer pageSize){
        RestResponse<Object> restResponse = new RestResponse<Object>();
        try {
			PageInfo<User> userList = userService.getUserByCondition(roleId, condition, pageNo, pageSize);
			restResponse.setCode(RestResponse.OK);
			restResponse.setData(userList);
			restResponse.setMessage("");
		} catch (Exception e) {
			restResponse.setCode(RestResponse.UNKNOWN_ERROR);
			restResponse.setData("");
			restResponse.setMessage(e.getMessage());
		}
		return restResponse;
    }
	
	@ApiOperation(value = "新增承运商主账号", notes = "新增承运商主账号")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "正常"),
            @ApiResponse(code = -1, message = "未知异常")})
    @RequestMapping(value = "/user/addcarriermainaccount", method = RequestMethod.POST)
    public RestResponse<Object> addCarrierMainAccount(@RequestBody JSONObject jsonObject){
        RestResponse<Object> restResponse = new RestResponse<Object>();
        try {
        	String carrierId = jsonObject.get("carrierid").toString();
			User user = userService.addCarrierMainAccount(carrierId);
			restResponse.setCode(RestResponse.OK);
			restResponse.setData(user);
			if (user==null){
				restResponse.setMessage("承运商id不能为空");
			} else {
				restResponse.setMessage(user.getId()!=-1?"主账号添加成功":"此承运商已有主账号");
			}
		} catch (Exception e) {
			restResponse.setCode(RestResponse.UNKNOWN_ERROR);
			restResponse.setData("");
			restResponse.setMessage(e.getMessage());
		}
		return restResponse;
    }
	
	@ApiOperation(value = "新增承运商子账号", notes = "新增承运商子账号")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "正常"),
            @ApiResponse(code = 3027, message = "请求参数错")})
    @RequestMapping(value = "/user/addcarriersubaccount", method = RequestMethod.POST)
    public RestResponse<Object> addCarrierSubAccount(@RequestBody JSONObject jsonObject){
        RestResponse<Object> restResponse = new RestResponse<Object>();
        try {
        	String carrierId = jsonObject.get("carrierid").toString();
        	int userNum = jsonObject.getInteger("userNum");
			List<User> userList = userService.addCarrierSubAccount(carrierId, userNum);
			restResponse.setCode(RestResponse.OK);
			restResponse.setData(userList);
			if(userList==null) {
				restResponse.setMessage("必须指定需要创建的子账号个数和有效的承运商名称!");
			} else {
				restResponse.setMessage(userList.size()==0?"先建立承运商主账号":"已经成功创建"+userList.size()+"个子账号");
			}
		} catch (Exception e) {
			restResponse.setCode(RestResponse.UNKNOWN_ERROR);
			restResponse.setData("");
			restResponse.setMessage(e.getMessage());
		}
		return restResponse;
    }
}
