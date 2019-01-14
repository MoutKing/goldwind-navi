package com.gw.web.common.base.pagination;

import java.util.ArrayList;
import java.util.List;

/**
 * 排序辅助类；
 * 后台Java构建一个order by 之后的排序部分语句；
 *  Order order = new Order();
 *  order.asc("field1").desc("field2")...
 *  显示：
 *  order.toSqlString();
 * 
 * @author maxiao
 */
public class Order {

	public static final String DESC = "desc";
	public static final String ASC = "asc";
	/**
	 * 是否反正顺序
	 */
	private boolean positive = true;
	/**
	 * 字段排序列表
	 */
	private List<OrderFragment> orderList = new ArrayList<OrderFragment>();
	
	/**
	 * 字段排序（内部类）
	 * 包装一个字段的顺序
	 * 
	 */
	private class OrderFragment{
		/**
		 * 列名
		 */
		String columnName;
		/**
		 * 是否升序
		 */
		boolean ascending;
		/**
		 * 实例化字段排序对象（内部使用）;
		 * 请勿直接使用此构造函数
		 * @param colName 列名
		 * @param asc 是否升序
		 */
		OrderFragment(String colName, boolean asc){
			if(!colName.matches("\\w+")){//防止SQL注入
				throw new IllegalArgumentException("Invalid column name.");
			}
			// mysql汉字排序混乱，加上这个转换以便按拼音排序
			//this.columnName = colName;
			this.columnName = "CONVERT("+colName+" USING gbk)";
			this.ascending = asc;
		}
		
		/**
		 * 获取SQL字符串
		 * @return 字符串
		 */
		String toSqlString(){
			return new StringBuffer(this.columnName).append(" ")
				.append((positive && ascending) ? ASC : DESC)
				.toString();
		}
	}

	public static Order parse(String orders){
		Order order = new Order();
		String[] tmp = orders.split(",");
		for(String t : tmp){
			String[] p = t.split(" ");
			if(p.length != 2){
				throw new IllegalArgumentException("Invalid order string.");
			}
			if(ASC.equalsIgnoreCase(p[1])){
				order.asc(p[0]);
			}else if(DESC.equalsIgnoreCase(p[1])){
				order.desc(p[0]);
			}else{
				throw new IllegalArgumentException("Invalid order string.");
			}
		}
		return order;
	}
	
	/**
	 * 实例化新的Order对象
	 * 内部排序列表为空
	 */
	public Order(){}
	
	/**
	 * 反向Order排序
	 * @return 对象本身
	 */
	public Order reverse(){
		this.positive = !this.positive ;
		return this;
	}
	
	/**
	 * 增加一个字段降序
	 * @param columnName
	 * @return 对象本身
	 */
	public Order desc(String columnName){
		orderList.add(new OrderFragment(columnName, false));
		return this;
	}
	
	/**
	 * 增加一个字段升序
	 * @param columnName
	 * @return 对象本身
	 */
	public Order asc(String columnName){
		orderList.add(new OrderFragment(columnName, true));
		return this;
	}
	
	/**
	 * 转化为排序字符串数组
	 * @return
	 */
	public String[] toOrderArray(){
		String[] orders = new String[orderList.size()];
		int i = 0;
		for(OrderFragment rf : orderList){
			orders[i++] = rf.toSqlString();
		}
		return orders;
	}
	
	/**
	 * 转化为可使用的SQL语句order by部分
	 * @return SQL字符串
	 */
	public String toSqlString(){
		if(orderList.size() == 0){
			return "";
		}
		StringBuilder sb = new StringBuilder(" ORDER BY ");
		for(int i = 0, len = orderList.size(); i < len; i++){
			if(i > 0){
				sb.append(", ");
			}
			sb.append(orderList.get(i).toSqlString());
		}
		return sb.toString();
	}
	
	
	/**
	 * 重载了Object的toString方法，方便打印调试
	 * @see #toSqlString()
	 * @see Object#toString()
	 */
	public String toString(){
		return toSqlString();
	}
	
	/**
	 * 排序的字段个数
	 * @return
	 */
	public int size(){
		return orderList.size();
	}
}
