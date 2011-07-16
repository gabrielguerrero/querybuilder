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
package com.querybuilder.sql;

public class SqlQueryFactory {

	
	private final ConnectionProvider connectionProvider;
	private SqlLimitHandler limitHandler;

	public SqlQueryFactory(ConnectionProvider connectionProvider) {
		super();
		this.connectionProvider = connectionProvider;
	}
	
	public void setLimitHandler(SqlLimitHandler limitHandler) {
		this.limitHandler = limitHandler;
	}
	
	public SqlSelectQueryBuilder newSelectQueryBuilder(){
		return new SqlSelectQueryBuilder(new SQLExecutor(connectionProvider.get()), limitHandler);
	}
	
	public SqlInsertQueryBuilder newInsertQueryBuilder(){
		return new SqlInsertQueryBuilder(new SQLExecutor(connectionProvider.get()));
	}
	
	public SqlUpdateQueryBuilder newUpdateQueryBuilder(){
		return new SqlUpdateQueryBuilder(new SQLExecutor(connectionProvider.get()));
	}
	
	public SqlDeleteQueryBuilder newDeleteQueryBuilder(){
		return new SqlDeleteQueryBuilder(new SQLExecutor(connectionProvider.get()));
	}
}
