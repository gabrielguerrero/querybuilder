package com.g2software.querybuilder.sql;

import java.sql.Connection;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.g2software.querybuilder.SelectQueryBuilder;

public class SqlSelectQueryBuilder extends SelectQueryBuilder<SqlSelectQueryBuilder, SQLExecutor> {

	@SuppressWarnings("rawtypes")
	private SqlResultTransformer resultTransformer;
	private SqlLimitHandler limitHandler;
	
	public SqlSelectQueryBuilder(SQLExecutor queryExecutor,SqlLimitHandler limitHandler) {
		super(queryExecutor);
		this.limitHandler = limitHandler;
	}
	
	public SqlSelectQueryBuilder(Connection connection,SqlLimitHandler limitHandler) {
		this(new SQLExecutor(connection),limitHandler);
	}

	@Override
	protected SqlSelectQueryBuilder newQueryBuilder() {
		return null;
	}

	public SqlSelectQueryBuilder setResultTransformer(SqlResultTransformer<?> resultTransformer) {
		this.resultTransformer = resultTransformer;
		return this;
	}
	
	@SuppressWarnings("rawtypes")
	public SqlResultTransformer getResultTransformer() {
		return resultTransformer;
	}
	
	@Override
	public Object clone() {
		SqlSelectQueryBuilder queryBuilder = (SqlSelectQueryBuilder) super.clone();
		queryBuilder.setResultTransformer(resultTransformer);
		return queryBuilder;
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SqlSelectQueryBuilder buildQuery() {
		super.buildQuery();
		String sql = getBuiltQuery();
		if (limitHandler!=null){
			sql = limitHandler.addQueryLimits(sql, getMaxResults(), getFirstResults());
		}
		Set<Map.Entry> parameters2 = getParameters().getParameters().entrySet();
		for (Map.Entry entry : parameters2) {
			if (entry.getValue() instanceof SqlSelectQueryBuilder){
				SelectQueryBuilder subquery = (SelectQueryBuilder) entry.getValue();
				subquery.buildQuery();
				String subsql = subquery.getBuiltQuery();
				StringBuilder builder = new StringBuilder('(');
				builder.append(subsql);
				builder.append(')');
				sql.replaceAll("\\:"+entry.getKey(), builder.toString());
				getParameters().getParameters().putAll(subquery.getParameters().getParameters());
			} 
//			else	if (entry.getValue() instanceof Collection){
//					Collection c = (Collection) entry.getValue();
//					StringBuilder builder = new StringBuilder("(");
//					for (Object object : c) {
//						if (object instanceof String){
//							builder.append("'");
//							builder.append(object);
//							builder.append("'");
//						} else {
//							builder.append(object);
//						}
//					}
//					builder.append(")");
//					sql.replaceAll("\\:"+entry.getKey(), builder.toString());
//			}
		}
		setBuiltQuery(sql);
		return this;
	}
}
