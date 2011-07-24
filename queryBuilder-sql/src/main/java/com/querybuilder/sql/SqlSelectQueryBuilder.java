package com.querybuilder.sql;

import java.sql.Connection;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.querybuilder.SelectQueryBuilder;
import com.querybuilder.sql.transform.SqlResultTransformer;

/*
 * Copyright (c) 2011 Gabriel Guerrero.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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
	
	public SqlSelectQueryBuilder(ConnectionProvider connectionProvider,SqlLimitHandler limitHandler) {
		this(new SQLExecutor(connectionProvider),limitHandler);
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
