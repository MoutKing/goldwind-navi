package com.gw.web.userCenter.dao;

import com.gw.web.userCenter.model.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
//	List<User> getByMap(Map<String, Object> map);

	User getById(Integer id);

// 	Integer create(User user);
//
//	int update(User user);

	User getByUserName(String username);

	List<User> getUserListByRole(Integer roleId, Integer pageNo, Integer pageSize);

	List<User> getUserByCondition(Integer roleId, String condition, Integer pageNo, Integer pageSize);

	List<User> getUserListByGW();
	
	int getCurrentCarrierMainAccountExist(String departmentId);

	Integer addUser(User user);

	Integer assignRoleForUser(Integer userId, Integer roleId);
	
	Integer deleteUser(String userId);

}
