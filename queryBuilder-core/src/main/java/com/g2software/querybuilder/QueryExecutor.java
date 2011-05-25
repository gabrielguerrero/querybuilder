package com.g2software.querybuilder;

import java.util.List;
import java.util.Map;

public abstract class QueryExecutor {
	@SuppressWarnings("unchecked")
	private QueryBuilder queryBuilder;

	protected QueryExecutor() {
	}

	public abstract void close();

	@SuppressWarnings("unchecked")
	protected QueryBuilder getQueryBuilder() {
		return queryBuilder;
	}

	public abstract <T> List<T> getResultList();

	public abstract <K,V> Map<K,V> getResultMap();

	public abstract <T> T getUniqueResult();

	@SuppressWarnings("unchecked")
	protected void init(QueryBuilder queryBuilder) {
		this.queryBuilder = queryBuilder;
	}
}
