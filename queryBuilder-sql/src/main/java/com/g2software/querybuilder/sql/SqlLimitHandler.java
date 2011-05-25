package com.g2software.querybuilder.sql;

public interface SqlLimitHandler {

	public String addQueryLimits(String originalQuery,int limit,int offset);
	
}
