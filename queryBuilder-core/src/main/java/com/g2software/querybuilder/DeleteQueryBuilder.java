package com.g2software.querybuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class DeleteQueryBuilder<E extends DeleteQueryBuilder<?, ?>, T extends QueryExecutor>
		extends QueryBuilder<E, T> {

	private String table;
	
	private WhereClause<E> conditions = new WhereClause<E>(this);
	
	public DeleteQueryBuilder(T queryExecutor) {
		super(queryExecutor);
	}

	public E deleteFrom(String table) {
		queryChanged();
		this.table = table;
		return (E) this;
	}
	
	public String getDeleteTable() {
		return table;
	}
	
	public WhereClause<E> where() {
		queryChanged();
		return conditions;
	}

	public E where(String... conditions) {
		where().clear();
		for (int i = 0; i < conditions.length; i++) {
			where().addAnd(conditions[i]);
		}
		return where().end();
	}
	
	@Override
	public E build() {

		StringBuilder queryWriter = new StringBuilder("delete from "+getDeleteTable());

		if (!where().isEmpty()) {
			queryWriter.append(" where ");
			queryWriter.append(where().toString());
		}	
		
		setBuiltQuery(queryWriter.toString());
		
		Map newParameters = new HashMap();
		Set<Map.Entry> parameters2 = getParameters().getParameters().entrySet();
		for (Map.Entry entry : parameters2) {
			if (entry.getValue() instanceof SelectQueryBuilder){
				SelectQueryBuilder subquery = (SelectQueryBuilder) entry.getValue();
				subquery.build();
				String subsql = subquery.getBuiltQuery();
				StringBuilder builder = new StringBuilder();
				builder.append('(');
				builder.append(subsql);
				builder.append(')');
				String sql = getBuiltQuery();
				subsql = builder.toString();
				sql = sql.replaceAll("\\:"+entry.getKey(), subsql);
				newParameters.putAll(subquery.getParameters().getParameters());
				setBuiltQuery(sql);
			} 
		}
		getParameters().getParameters().putAll(newParameters);
		return (E) this;
	}

	public E setParameter(String name, Object value) {
		getParameters().add(name, value);
		return (E) this;
	}


	@Override
	public String toString() {
		return "query: " + getBuiltQuery() + " parameters: " + getParameters();
	}

	protected abstract E newQueryBuilder();

	public Object clone() {
		E queryBuilder = newQueryBuilder();
		queryBuilder.setParameters(getParameters());
		return queryBuilder;
	}

}
