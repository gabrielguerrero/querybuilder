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
		return new SqlSelectQueryBuilder(getQueryExecutor(), limitHandler);
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
	public SqlSelectQueryBuilder build() {
		super.build();
		String sql = getBuiltQuery();
		if (limitHandler!=null){
			sql = limitHandler.addQueryLimits(sql, getMaxResults(), getFirstResults());
		}
		setBuiltQuery(sql);
		return this;
	}
}
