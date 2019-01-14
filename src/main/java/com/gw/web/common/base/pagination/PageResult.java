package com.gw.web.common.base.pagination;

import java.util.List;

/**
 * 分页查询结果
 * 
 * @author maxiao
 *
 * @param <T>
 */
public class PageResult<T>  extends Pagination{

	/**
	 * 对象数组，里面的对象具体见API描述
	 * @required
	 */
	public List<T> list;


	/**
	 * @return the list
	 */
	public List<T> getList() {
		return list;
	}


	/**
	 * @param list the list to set
	 */
	public void setList(List<T> list) {
		this.list = list;
	}


}
