package com.g2software.querybuilder.sql;

import java.sql.Connection;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.g2software.querybuilder.SelectQueryBuilder;
import com.g2software.querybuilder.UpdateQueryBuilder;

public class SqlUpdateQueryBuilder extends UpdateQueryBuilder<SqlUpdateQueryBuilder, SQLExecutor> {

	@SuppressWarnings("rawtypes")
	
	public SqlUpdateQueryBuilder(SQLExecutor queryExecutor) {
		super(queryExecutor);
	}
	
	public SqlUpdateQueryBuilder(Connection connection) {
		this(new SQLExecutor(connection));
	}

	@Override
	protected SqlUpdateQueryBuilder newQueryBuilder() {
		return null;
	}
	
	@Override
	public Object clone() {
		SqlUpdateQueryBuilder queryBuilder = (SqlUpdateQueryBuilder) super.clone();
		return queryBuilder;
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SqlUpdateQueryBuilder build() {
		super.build();
		String sql = getBuiltQuery();
		Set<Map.Entry> parameters2 = getParameters().getParameters().entrySet();
		for (Map.Entry entry : parameters2) {
			if (entry.getValue() instanceof SqlUpdateQueryBuilder){
				SelectQueryBuilder subquery = (SelectQueryBuilder) entry.getValue();
				subquery.build();
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
