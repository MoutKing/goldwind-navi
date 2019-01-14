/**   
* @Title: Page.java 
* @Package com.bl.web.web.common.model 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhengsh  
* @date 2013-12-5 下午5:24:16 
* @version V1.0   
*/
package com.gw.web.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhengsh
 *
 */
public class Page {
	private int pageNo;
	private int startNo;
	private int endNo;
	private int pageSize;
	private int pageNumberSize;
	private int totalRecords;
	private int totalPageNumber;
	private int startPageNo;
	private int lastPageNo;
	private List<Integer> pageNoList;

	public Page() {
		pageNo = 1;
		pageSize = 10;
		pageNumberSize = 10;
		pageNoList = new ArrayList<Integer>();
	}

	/**
	 * @return the pageNo
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the startNo
	 */
	public int getStartNo() {
		return (pageNo - 1) * pageSize;
	}

	/**
	 * @param startNo the startNo to set
	 */
	public void setStartNo(int startNo) {
		this.startNo = startNo;
	}

	/**
	 * @return the endNo
	 */
	public int getEndNo() {
		int endNo = getStartNo() + pageSize;
		int _maxEndNo = totalRecords;
		if (_maxEndNo < endNo) {
			endNo = _maxEndNo;
		}
		return endNo;
	}

	/**
	 * @param endNo the endNo to set
	 */
	public void setEndNo(int endNo) {
		this.endNo = endNo;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the pageNumerSize
	 */
	public int getPageNumberSize() {
		return pageNumberSize;
	}

	/**
	 * @param pageNumerSize the pageNumerSize to set
	 */
	public void setPageNumberSize(int pageNumberSize) {
		this.pageNumberSize = pageNumberSize;
	}

	/**
	 * @return the totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @param totalRecords the totalRecords to set
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * @return the totalPageNumber
	 */
	public int getTotalPageNumber() {
		totalPageNumber = (totalRecords % pageSize == 0) ? (totalRecords / pageSize) : (totalRecords / pageSize + 1);
		return totalPageNumber;
	}

	/**
	 * @param totalPageNumber the totalPageNumber to set
	 */
	public void setTotalPageNumber(int totalPageNumber) {
		this.totalPageNumber = totalPageNumber;
	}

	/**
	 * @return the startPageNo
	 */
	public int getStartPageNo() {
		return startPageNo;
	}

	/**
	 * @return the lastPageNo
	 */
	public int getLastPageNo() {
		return lastPageNo;
	}

	/**
	 * @return the pageNoList
	 */
	public List<Integer> getPageNoList() {
		pageNoList = new ArrayList<Integer>();
//		if(pageNo % pageNumberSize == 0 ){
//			startPageNo = pageNo - pageNumberSize + 1;
//		} else {
//			startPageNo = pageNo - pageNo % pageNumberSize + 1;
//		}
//		lastPageNo = startPageNo + pageNumberSize;
//		int maxPageNo = getTotalPageNumber() + 1;
//		if (lastPageNo > maxPageNo) {
//			lastPageNo = maxPageNo;
//		}
		
//		if(pageNo - 2 > 0){
//				if(pageNo + 2 <totalPageNumber){
//					startPageNo = pageNo -2;
//				} else {
//					startPageNo = totalPageNumber - pageNumberSize + 1;
//				}
//			
//		} else {
//			startPageNo = 1;
//		}
//		
//		if(pageNo + 3 - startPageNo < pageNumberSize){
//			lastPageNo = startPageNo + pageNumberSize - 1;
//		} else {
//			if(pageNo + 2 > totalPageNumber){
//				lastPageNo = totalPageNumber;
//			} else {
//				lastPageNo = pageNo + 2;
//			}
//		}
		
		if(totalPageNumber <= pageNumberSize){
			startPageNo = 1;
			lastPageNo = totalPageNumber;
		} else {
			if(pageNo -2 <= 0){
				startPageNo = 1;
				lastPageNo = pageNumberSize;
			} else if(pageNo + 2 >= totalPageNumber){
				startPageNo = totalPageNumber - pageNumberSize + 1;
				lastPageNo = totalPageNumber;
			} else {
				startPageNo = pageNo - 2;
				lastPageNo = pageNo + 2;
			}
		}
		
		for (int i = startPageNo; i <= lastPageNo; i++) {
			pageNoList.add(i);
		}
		return pageNoList;
	}

	public static void main(String[] args) {
		Page page = new Page();
		//		page.setPageNo(1);
		page.setTotalRecords(10);
		System.out.println(page.getTotalPageNumber());
		System.out.println(page.getPageNoList());
	}

}
