package com.g2software.querybuilder.sql;

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
