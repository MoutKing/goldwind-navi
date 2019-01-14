package com.gw.web.userCenter.controller;

import com.gw.web.common.base.rest.RestResponse;
import com.gw.web.common.exception.BadRequestException;
import com.gw.web.common.exception.ForbiddenException;
import com.gw.web.common.model.enums.Errors;
import com.gw.web.common.utils.RestUtil;
import com.gw.web.security.CustomUserService;
import com.gw.web.userCenter.model.User;
import com.gw.web.userCenter.model.UserCreateModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @program: gold-navi
 * @description:
 * @author: MengGuanghui
 * @create: 2019-01-07 11:42
 * @modifier: Jeremy Zhang
 **/
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CustomUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @ApiOperation(value = "用户登录", notes = "通过用户名、密码登录")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "正常"),
            @ApiResponse(code = 400, message = "输入参数不正确"),
            @ApiResponse(code = 403, message = "认证失败，没有权限访问"),
            @ApiResponse(code = 500, message = "服务器内部错误")})
    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public RestResponse login(HttpServletResponse response, HttpServletRequest request,
                                      @RequestBody UserCreateModel tSysUserCreateModel

    ) {
        String username = tSysUserCreateModel.getUsername();
        String password = tSysUserCreateModel.getPassword();

        if (username == null || username.isEmpty()) {
            throw new BadRequestException(Errors.PARAM_IS_NULL, "用户名不能为空");
        }

        // if (password == null || password.isEmpty() || password.length() != 32) {
        //     restResponse.setCode(RestResponse.UNKNOWN_ERROR);
        //     restResponse.setMessage("不符合密码规范");
        //     response.setStatus(400);
        //     return restResponse;
        // }

        logger.info("UC login name "+username+" checking username");
        UserDetails user = userService.loadUserByUsername(username);

        // 验证密码是否正确
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ForbiddenException(Errors.PARAM_NOT_FOUND, "用户名或密码错误");
        }
        logger.info("UC login name "+username+" checked success. user "+user.getUsername()+
                " Request Session "+ request.getSession().getId());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

        if (authenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User)) {
            throw new ForbiddenException(Errors.PARAM_NOT_FOUND, "用户名或密码错误");
        }
        //获取session
        HttpSession session   =   request.getSession();
        // 登录成功后 把username 和 userid 写入session
        request.getSession().setAttribute("username", user.getUsername());
        // request.getSession().setAttribute("userid", ((User) principal).getId());
        // request.getSession().setAttribute("email", user.getcEmail());
        // TODO 查询出用户的权限信息 写入session
        Map<String, List<String>> maps = new HashMap<String, List<String>>();
        List<String> permissions = new ArrayList<String>();
        Map<String, List<Object>> resources = new HashMap<String, List<Object>>();

        //-------用户功能权限和资源权限写入session-------------------
        // request.getSession().setAttribute("permissions", permissions);
        // request.getSession().setAttribute("resources", resources);
        // sessionService.saveUserNameSessionId(user.getUsername(), request.getSession().getId());//用户名与当前会话id绑定关系存储到redis

        Map<String,Object> datas = new HashMap<String,Object>();
        datas.put("permissions", permissions);
        // response.addCookie();
        return RestUtil.convertResponse(datas);
    }
}
