package com.g2software.querybuilder.sql;

public class PostgresqlLimitHandler implements SqlLimitHandler{

	public String addQueryLimits(String originalQuery, int limit, int offset) {
		StringBuilder builder = new  StringBuilder(originalQuery);
		if (limit>-1)
			builder.append(" limit "+limit);
		if(offset>-1)
			builder.append(" offset "+offset);
		return builder.toString();
	}

}
