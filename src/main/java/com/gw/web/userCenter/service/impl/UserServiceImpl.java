package com.gw.web.userCenter.service.impl;

import com.gw.web.userCenter.model.User;
import com.gw.web.userCenter.service.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.web.userCenter.dao.UserDao;

/**
 * @program: template
 * @description:
 * @author: MengGuanghui
 * @create: 2018-12-28 14:59
 **/

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    public User getById(Integer id) {
        User user = userDao.getById(id);
        return user;
    }

	@Override
	public User getUserList() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageInfo<User> getUserListByRole(Integer roleId, Integer pageNo, Integer pageSize) throws Exception {
		PageInfo<User> pageInfo = null;
		if(roleId>0) {//若角色ID有效
			PageHelper.startPage(pageNo,pageSize);
			List<User> userList = userDao.getUserListByRole(roleId,pageNo,pageSize);
			pageInfo = new PageInfo<>(userList);
		}
		return pageInfo;
	}

	@Override
	public PageInfo<User> getUserByCondition(Integer roleId, String condition, Integer pageNo, Integer pageSize) throws Exception {
		PageInfo<User> pageInfo = null;
		if(roleId>0&&null!=condition) {
			PageHelper.startPage(pageNo,pageSize);
			List<User> userList = userDao.getUserByCondition(roleId,condition,pageNo,pageSize);
			pageInfo = new PageInfo<>(userList);
		}
		return pageInfo;
	}

	@Override
	public Boolean getCurrentCarrierMainAccountExist(String departmentId) throws Exception {
		Boolean isCurrentCarrierMainAccountExist = false;
		if (!departmentId.isEmpty()) {
			int x = userDao.getCurrentCarrierMainAccountExist(departmentId);
			if ( x> 0) {
				isCurrentCarrierMainAccountExist = true;
			}
		}
		return isCurrentCarrierMainAccountExist;
	}

	@Override
	public User addCarrierMainAccount(String departmentId) throws Exception {
		if(!departmentId.isEmpty()) {
			User user = new User();
			if(!getCurrentCarrierMainAccountExist(departmentId)) {//当前承运商未有主账号
				List<User> uList = userDao.getUserListByGW();
				int newUserId = Integer.valueOf(uList.get(0).getUsername().substring(2));
				for(int i=1;i<uList.size();i++) {
					int currentUserId= Integer.valueOf(uList.get(i).getUsername().substring(2));
					if(currentUserId>newUserId) {
						newUserId = currentUserId;
					}
				}
				//查询user表中user_id最大者后+1
				user.setAccount(generateUserIdASC());
				user.setUsername(generateUserIdASC());
				user.setPassword("12346");
				user.setUsername("gw"+String.valueOf(newUserId+1));
				user.setAccount("gw"+String.valueOf(newUserId+1));
				user.setPassword("12345");
				user.setEnable(1);
				user.setIsLocked(1);
				user.setUnlockTime(new Date());
				user.setPosition("路勘工程师");
				user.setDepartmentId(departmentId);
				userDao.addUser(user);
				int userId = userDao.getByUserName(user.getUsername()).getId();
				userDao.assignRoleForUser(userId, 2);
				user.setPassword("12345");
				return user;
			} else {//当前承运商已建立主账号
				user.setId(-1);
				return user;
			}
		}
		return null;
	}

	@Override
	public List<User> addCarrierSubAccount(String departmentId, Integer userNum) throws Exception {
		if(!departmentId.isEmpty()||userNum==0) {
			List<User> userList = new ArrayList<>();
			if(getCurrentCarrierMainAccountExist(departmentId)) {//当前承运商已有主账号
				//新增承运商子账号
				int successInsertNum = 0;
				for(int i=0;i<userNum;i++) {
					User user = new User();
					//生成子账号的user_id
					user.setAccount(generateUserIdASC());
					user.setUsername(generateUserIdASC());//用户唯一标识
					user.setPassword("12346");
					user.setPassword("12345");
					user.setEnable(1);
					user.setIsLocked(1);
					user.setUnlockTime(new Date());
					user.setPosition("路勘工程师");
					user.setDepartmentId(departmentId);
					//向t_user中插入数据
					userDao.addUser(user);
					//向t_user_role中插入数据
					userDao.assignRoleForUser(user.getId(), 3);
					successInsertNum++;
					user.setPassword("12346");
					userList.add(user);
				}
				return userList;
			} else {
				return userList;
			}
		}
		return null;
	}

	@Override
	public String generateUserIdASC() {
		//查询以gw开头的所有user_id
		List<User> userList = userDao.getUserListByGW();
		//得出userList的最大值
		int newUserId = Integer.valueOf(userList.get(0).getUsername().substring(2));
		for(int i=1;i<userList.size();i++) {
			int currentUserId= Integer.valueOf(userList.get(i).getUsername().substring(2));
			if(currentUserId>newUserId) {
				newUserId = currentUserId;
			}
		}
		String userId = "gw"+String.valueOf(newUserId+1);
		return userId;
	}

}