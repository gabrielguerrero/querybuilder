package com.g2software.querybuilder;

public class QueryBuilderException extends RuntimeException {

	public QueryBuilderException(QueryBuilder queryBuilder, Throwable t) {
		super(queryBuilder.getBuiltQuery() + '\n'
				+ queryBuilder.getParameters(), t);
	}

	public QueryBuilderException(String message) {
		super(message);
	}

	public QueryBuilderException(String message, Throwable t) {
		super(message, t);
	}

}
