package com.g2software.querybuilder.sql;

import java.sql.Connection;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.g2software.querybuilder.InsertQueryBuilder;
import com.g2software.querybuilder.SelectQueryBuilder;

public class SqlInsertQueryBuilder extends InsertQueryBuilder<SqlInsertQueryBuilder, SQLExecutor> {

	@SuppressWarnings("rawtypes")
	
	public SqlInsertQueryBuilder(SQLExecutor queryExecutor) {
		super(queryExecutor);
	}
	
	public SqlInsertQueryBuilder(Connection connection) {
		this(new SQLExecutor(connection));
	}

	@Override
	protected SqlInsertQueryBuilder newQueryBuilder() {
		return null;
	}
	
	@Override
	public Object clone() {
		SqlInsertQueryBuilder queryBuilder = (SqlInsertQueryBuilder) super.clone();
		return queryBuilder;
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SqlInsertQueryBuilder buildQuery() {
		super.buildQuery();
		String sql = getBuiltQuery();
		Set<Map.Entry> parameters2 = getParameters().getParameters().entrySet();
		for (Map.Entry entry : parameters2) {
			if (entry.getValue() instanceof SqlInsertQueryBuilder){
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
