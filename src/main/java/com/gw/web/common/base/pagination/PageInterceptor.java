package com.gw.web.common.base.pagination;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * MyBatis分页Interceptor
 * 结合排序参数进行排序
 * 不处理查询条件
 * @author maxiao 
 * 2018/3/19
 * 
 */
@Intercepts({
	@Signature(type=StatementHandler.class, method="prepare", args={Connection.class, Integer.class })
})
@Component
public class PageInterceptor implements Interceptor {

	private int defaultSize = 10;
	private int maxSize = 1000;
	
	@Override
	public Object plugin(Object target) {
		if(target instanceof StatementHandler){
			return Plugin.wrap(target, this);
		}
		return target;
	}
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
		MetaObject handlerMetaObject = SystemMetaObject.forObject(handler);
		StatementHandler delegate = (StatementHandler) handlerMetaObject.getValue("delegate");
		BoundSql boundSql = delegate.getBoundSql();
		Object paramObj = boundSql.getParameterObject();
		
		// 判断参数里是否有pages对象,order对象
		Pagination pages = null;
		Order order = null;
		
		if (paramObj instanceof Map) {
			for (Object arg : ((Map) paramObj).values()) {
				if(pages == null && arg instanceof Pagination){
					pages = (Pagination) arg;
					if(order != null){
						break;
					}
					continue;
				}
				if(order == null && arg instanceof Order){
					order = (Order) arg;
					if(pages != null){
						break;
					}
					continue;
				}
			}
		} else if (paramObj instanceof Pagination) {
			pages = (Pagination) paramObj;
		} else if(paramObj instanceof Order){
			order = (Order) paramObj;
		}
		
		MappedStatement mappedStatement = null;
		String orderBy = null;
		
		if(order != null && order.size() > 0){
			MetaObject statementMetaObject = SystemMetaObject.forObject(delegate);
			mappedStatement = (MappedStatement) statementMetaObject.getValue("mappedStatement");
			orderBy = getOrderBy(order, mappedStatement);
		}
		
		if (pages != null) {
			// 通过反射获取delegate父类BaseStatementHandler的mappedStatement属性
			if(mappedStatement == null){
				MetaObject statementMetaObject = SystemMetaObject.forObject(delegate);
				mappedStatement = (MappedStatement) statementMetaObject.getValue("mappedStatement");
			}
			// 拦截到的prepare方法参数是一个Connection对象
			Connection connection = (Connection) invocation.getArgs()[0];
			
			// 获取当前要执行的Sql语句，也就是我们直接在Mapper映射语句中写的Sql语句
			count(boundSql, mappedStatement, connection, pages);
			
			String sql = boundSql.getSql();
			// 获取分页Sql语句
			String pageSql = getPageSql(pages, orderBy == null ? sql : sql + orderBy);
			// 利用反射设置当前BoundSql对应的sql属性建立好的分页Sql语句
			MetaObject boundSqlMetaObject = SystemMetaObject.forObject(boundSql);
			boundSqlMetaObject.setValue("sql", pageSql);
			
		}else if(orderBy != null){ //只有排序
			String sql = boundSql.getSql();
			MetaObject boundSqlMetaObject = SystemMetaObject.forObject(boundSql);
			boundSqlMetaObject.setValue("sql", sql + orderBy);
		}
	
		return invocation.proceed();
	}

	@Override
	public void setProperties(Properties properties) {

	}
	
	/**
	 * 获取定义中的字段映射
	 * @param rm
	 * @return
	 */
	private Map<String, String> getMappings(ResultMap rm){
		Map<String, String> map = new TreeMap<>();
		for(ResultMapping m : rm.getIdResultMappings()){
			map.put(m.getProperty(), m.getColumn());
		}
		for(ResultMapping m : rm.getPropertyResultMappings()){
			map.put(m.getProperty(), m.getColumn());
		}
		return map;
	}
	
	private String getOrderBy(Order order, MappedStatement mappedStatement){
		Map<String, String> mappings = getMappings(mappedStatement.getResultMaps().get(0));
		if(mappings.size() == 0){
			return order.toSqlString();
		}else{
			String[] oa = order.toOrderArray();
			StringBuilder sb = new StringBuilder(" ORDER BY ");
			
			for(int i = 0; i < oa.length; i++){
				String[] tmp  = oa[i].split(" ");
				if(i > 0){
					sb.append(",");
				}
				if(mappings.containsKey(tmp[0])){
					sb.append(mappings.get(tmp[0])).append(" ").append(tmp[1]);
				}else{
					sb.append(oa[i]);
				}
			}
			return sb.toString();
		}
	}
	
	private String getPageSql(Pagination pages, String sql) {
		StringBuffer sqlBuffer = new StringBuffer(sql);
//		不同数据库的分页应有不同实现
		int offset = (pages.page - 1) * pages.size;
		sqlBuffer.append(" limit ").append(offset).append(",").append(pages.size);
		return sqlBuffer.toString();
		
	}
	
	private String getCountSql(String sql){
		return "select count(*) from (" + sql + ") as tmp_total";
	}
	
	private void count(BoundSql boundSql,
			MappedStatement mappedStatement,
			Connection connection, Pagination pages) throws SQLException{
		
		String countSql = getCountSql(boundSql.getSql());
		BoundSql countBoundSql = new SubBoundSql(mappedStatement.getConfiguration(), countSql, boundSql);
		ParameterHandler parameterHandler =
				new DefaultParameterHandler(mappedStatement, boundSql.getParameterObject(), countBoundSql);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(countSql);
			// 通过parameterHandler给PreparedStatement对象设置参数
			parameterHandler.setParameters(pstmt);
			// 执行获取总记录数的Sql语句和获取结果了
			rs = pstmt.executeQuery();
			if (rs.next()) {
				pages.total = rs.getLong(1);
			}
			
			//调整分页各项指标，使其合理
			if(pages.size <= 0){
				pages.size = defaultSize;
			}else if(pages.size > 1000){
				pages.size = maxSize;
			}
			int maxIndex = (int) Math.ceil((double)pages.total/pages.size);
			if(maxIndex == 0){
				maxIndex = 1;
			}
			
			if(pages.page <= 0){
				pages.page = 1;
			}else if(pages.page > maxIndex){
				pages.page = maxIndex;
			}
			
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				throw e;
			}
		}
	}
	
	
	class SubBoundSql extends BoundSql {

		BoundSql parentBoundSql;

		public SubBoundSql(Configuration configuration, String sql, BoundSql parentBoundSql) {
			super(configuration, sql, null, null);
			this.parentBoundSql = parentBoundSql;
		}

		public List<ParameterMapping> getParameterMappings() {
			return parentBoundSql.getParameterMappings();
		}

		public Object getParameterObject() {
			return parentBoundSql.getParameterObject();
		}

		public boolean hasAdditionalParameter(String name) {
			return parentBoundSql.hasAdditionalParameter(name);
		}

		public void setAdditionalParameter(String name, Object value) {
			parentBoundSql.setAdditionalParameter(name, value);
		}

		public Object getAdditionalParameter(String name) {
			return parentBoundSql.getAdditionalParameter(name);
		}
	}
		

}
