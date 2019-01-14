package com.gw.web.userCenter.model;

import java.io.Serializable;

/**
 * @program: template
 * @description:
 * @author: MengGuanghui
 * @create: 2018-12-28 13:58
 **/
public class Role implements Comparable<Role>, Serializable {
    private Integer id;
    private String roleName;
    private String roleDescription;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    @Override
    public int compareTo(Role o) {
        if(id == o.getId()){
            return 0;
        }else if(id > o.getId()){
            return 1;
        }else{
            return -1;
        }
    }

}
