package com.g2software.querybuilder;

import java.util.LinkedList;


public abstract class UpdateQueryBuilder<E extends UpdateQueryBuilder<?, ?>, T extends QueryExecutor>
		extends QueryBuilder<E, T> {

	private String table;
	
	private FieldsCollection<E> fields = new FieldsCollection<E>(this);
	private WhereClause<E> conditions = new WhereClause<E>(this);
	
	private SelectQueryBuilder<SelectQueryBuilder<?,?>, T> valuesFrom;
	
	public UpdateQueryBuilder(T queryExecutor) {
		super(queryExecutor);
	}

	public void update(String table) {
		this.table = table;
	}
	
	public String getUpdateTable() {
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

		StringBuilder queryWriter = new StringBuilder("update "+getUpdateTable());

		queryWriter.append("set ");
		LinkedList<Field> fieldsList = fields.getFieldsList();
		for (int i = 0;i<fields().size();i++) {
			Field f = fieldsList.get(i);
			Object value = getParameters().get(f.getName());
			if (i>0)
				queryWriter.append(',');
			queryWriter.append(f.getName());
			if (value instanceof SelectQueryBuilder){
				SelectQueryBuilder subquery = (SelectQueryBuilder) value;
				subquery.buildQuery();
				String sql = subquery.getBuiltQuery();
				queryWriter.append(" = (");
				queryWriter.append(sql);
				queryWriter.append(") ");
				getParameters().getParameters().putAll(subquery.getParameters().getParameters());
			} else {
				queryWriter.append(" = :");
				queryWriter.append(" = :"+f.getName());
			}
			
		}
		
		if (!where().isEmpty()) {
			queryWriter.append(" where ");
			queryWriter.append(where().toString());
		}	
		
		setBuiltQuery(queryWriter.toString());
		return (E) this;
	}



	public FieldsCollection<E> fields() {
		return fields;
	}

	public E fields(String... fields) {
		fields().clear();
		for (int i = 0; i < fields.length; i++) {
			fields().add(fields[i]);
		}
		return fields().end();
	}

	public E setFieldValue(String name, Object value){
		fields().add(name);
		setParameter(name, value);
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
		queryBuilder.setFields(fields);
		queryBuilder.setParameters(getParameters());
		return queryBuilder;
	}

	@SuppressWarnings("unchecked")
	private void setFields(FieldsCollection<?> fields) {
		this.fields = (FieldsCollection<E>) fields;
	}

}
