package com.gw.web.common.base.pagination;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页查询对象
 * 
 * @author MengGuanghui
 */
public class PageQuery {

	/**
	 * 查询参数对象，具体见各API的描述
	 */
	public Map<String, Object> params= new HashMap<String, Object>();
	
	// public T condition;
	
	/**
	 * 分页参数
	 */
	public Pagination pages;

	/**
	 * 排序: 字段名 (ase|desc), 多个字段排序逗号分隔；需要API后台支持
	 */
	public Order order;
	
	public void setOrder(String orders){
		order = Order.parse(orders);
	}

	/**
	 * @return the pages
	 */
	public Pagination getPages() {
		return pages;
	}

	/**
	 * @param pages the pages to set
	 */
	public void setPages(Pagination pages) {
		this.pages = pages;
	}
	
}
