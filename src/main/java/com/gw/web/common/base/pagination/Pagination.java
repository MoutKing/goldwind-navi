package com.gw.web.common.base.pagination;

/**
 * 分页参数对象
 * @author maxiao
 */
public class Pagination {

	/**
	 * 当前页数
	 */
	public int page;
	/**
	 * 每页记录数
	 */
	public int size;
	/**
	 * 当前记录总数
	 */
	public long total;

	public Pagination() {}
	
	/**
	 * 分页标注
	 * @param page 当前页数
	 * @param size 分页大小
	 * @param total 总记录条数
	 */
	public Pagination(int page, int size, int total) {
		if(page <=0) page = 1;
		if(size <=0) size = 20;
		if(total <0) size = 0;

		this.page = page;
		this.size = size;
		this.total = total;
	}

	/**
	 * 返回一个新的分页标注,使用默认设定：{page:1, size:20, total:0}
	 * @return
	 */
	public static Pagination defaultPagination(){
		return new Pagination(1, 20, 0);
	}

	/**
	 * @return the index
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return the total
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * @param page the index to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(long total) {
		this.total = total;
	}


}
