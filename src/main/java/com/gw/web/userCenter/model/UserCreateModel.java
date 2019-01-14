package com.gw.web.userCenter.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @program: template
 * @description:
 * @author: MengGuanghui
 * @create: 2019-01-04 14:19
 **/

@ApiModel(value = "创建用户模型", description = "用户对象user")
public class UserCreateModel {
    @ApiModelProperty(value = "用户登录名", name = "username", required = true, example = "wangguiyu")
    private String username;// 登录名

    @ApiModelProperty(value = "用户登录密码", name = "password", required = true)
    private String password;// 密码

    @ApiModelProperty(value = "用户中文名称", name = "cname", required = true, example = "王贵宇")
    private String cname;// 用户中文名称

    @ApiModelProperty(value = "用户email", name = "cname", required = true, example = "abc_123@goldwind.com.cn")
    private String cemail;// 用户用户email

    @ApiModelProperty(value = "用户角色,用半角逗号分隔", name = "roleIdList", required = true, example = "[1,2,3]")
    private List<Integer> roleIdList ;

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the cname
     */
    public String getCname() {
        return cname;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param cname
     *            the cname to set
     */
    public void setCname(String cname) {
        this.cname = cname;
    }

    /**
     * @return the roleIdList
     */
    public List<Integer> getRoleIdList() {
        return roleIdList;
    }

    /**
     * @param roleIdList the roles to set
     */
    public void setRoleIdList(List<Integer> roleIdList) {
        this.roleIdList = roleIdList;
    }

    /**
     * @return the cemail
     */
    public String getCemail() {
        return cemail;
    }

    /**
     * @param cemail the cemail to set
     */
    public void setCemail(String cemail) {
        this.cemail = cemail;
    }
}
