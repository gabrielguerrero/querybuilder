package com.g2software.querybuilder;

public abstract class DeleteQueryBuilder<E extends DeleteQueryBuilder<?, ?>, T extends QueryExecutor>
		extends QueryBuilder<E, T> {

	private String table;
	
	private WhereClause<E> conditions = new WhereClause<E>(this);
	
	public DeleteQueryBuilder(T queryExecutor) {
		super(queryExecutor);
	}

	public void deleteFrom(String table) {
		this.table = table;
	}
	
	public String getDeleteTable() {
		return table;
	}
	
	public WhereClause<E> where() {
		return conditions;
	}

	public WhereClause<E> where(String where) {
		return where().clear().addAnd(where);
	}
	
	@Override
	public E buildQuery() {

		StringBuilder queryWriter = new StringBuilder("delete from "+getDeleteTable());

		if (!where().isEmpty()) {
			queryWriter.append(" where ");
			queryWriter.append(where().toString());
		}	
		
		setBuiltQuery(queryWriter.toString());
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
