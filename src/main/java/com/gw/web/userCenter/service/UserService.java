package com.gw.web.userCenter.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gw.web.userCenter.model.User;

public interface UserService {
	public User getById(Integer id) throws Exception;

	public User getUserList() throws Exception;

	public PageInfo<User> getUserListByRole(Integer roleId, Integer pageNo, Integer pageSize) throws Exception;

	public PageInfo<User> getUserByCondition(Integer roleId, String condition, Integer pageNo, Integer pageSize)
			throws Exception;

	public User addCarrierMainAccount(String departmentId) throws Exception;
	
	public List<User> addCarrierSubAccount(String departmentId, Integer userNum) throws Exception;

	public Boolean getCurrentCarrierMainAccountExist(String departmentId) throws Exception;
	
	public String generateUserIdASC();
	
}