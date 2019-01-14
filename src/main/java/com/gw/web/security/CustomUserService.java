package com.gw.web.security;

import com.gw.web.common.exception.ForbiddenException;
import com.gw.web.common.model.enums.Errors;
import com.gw.web.userCenter.dao.PermissionDao;
import com.gw.web.userCenter.dao.RoleDao;
import com.gw.web.userCenter.dao.UserDao;
import com.gw.web.userCenter.model.Permission;
import com.gw.web.userCenter.model.Role;
import com.gw.web.userCenter.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: template
 * @description:
 * @author: MengGuanghui
 * @modifier: Jeremy Zhang
 * @create: 2018-12-28 13:50
 **/
@Service
public class CustomUserService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    PermissionDao permissionDao;

    @Override
    public UserDetails loadUserByUsername(String userName) { //重写loadUserByUsername 方法获得 userdetails 类型用户

        User user = userDao.getByUserName(userName);

        if (user != null) {
            logger.info(user.getUsername());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

            List<Role> roleList = roleDao.getByUserId(user.getId());
            user.setRoles(roleList);

            for(Role r : roleList) {
                List<Permission> permissions = permissionDao.getByRoleId(r.getId());
                for (Permission permission : permissions) {
                    if (permission != null && permission.getPermissionName()!=null) {
                        GrantedAuthority grantedAuthority = new CustomGrantedAuthority(permission.getApiUrl(), permission.getPermissionName());
                        grantedAuthorities.add(grantedAuthority);
                    }
                }
            }

            user.setGrantedAuthorities(grantedAuthorities);
            return user;
        } else {
            throw new ForbiddenException(Errors.PARAM_NOT_FOUND,"用户名或密码错误");
        }
    }
}
