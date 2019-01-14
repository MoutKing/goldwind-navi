package com.gw.web.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.web.test.dao.TestDao;
import com.gw.web.test.service.TestService;

@Service("testService")
public class TestServiceImpl implements TestService {
	@Autowired 
	private TestDao testDao;
	
	public Integer getIntRslt() throws Exception{
		return testDao.getIntRslt();
	} 
}
