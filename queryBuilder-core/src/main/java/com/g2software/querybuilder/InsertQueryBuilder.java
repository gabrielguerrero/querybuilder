package com.g2software.querybuilder;

import java.util.LinkedList;


public abstract class InsertQueryBuilder<E extends InsertQueryBuilder<?, ?>, T extends QueryExecutor>
		extends QueryBuilder<E, T> {

	private String table;
	
	private FieldsCollection<E> fields = new FieldsCollection<E>(this);

	private SelectQueryBuilder<SelectQueryBuilder<?,?>, T> valuesFrom;
	
	public InsertQueryBuilder(T queryExecutor) {
		super(queryExecutor);
	}

	public InsertQueryBuilder<E,T> insertInto(String table) {
		this.table = table;
		return this;
	}
	
	public InsertQueryBuilder<E,T> setValuesFrom(SelectQueryBuilder<SelectQueryBuilder<?,?>, T> valuesFrom){
		this.valuesFrom = valuesFrom;
		return this;
	}
	
	public String getInsertTable() {
		return table;
	}
	@Override
	public E buildQuery() {

		StringBuilder queryWriter = new StringBuilder("insert into "+getInsertTable());
		queryWriter.append(" (");

		fields().joinBy(',', queryWriter);

		queryWriter.append(") ");

		if (valuesFrom!=null){
			String selectQuery = valuesFrom.buildQuery().getBuiltQuery();
			queryWriter.append(selectQuery);
		} else {
			queryWriter.append(" (");
			
			LinkedList<Field> fieldsList = fields.getFieldsList();
			for (int i = 0;i<fields().size();i++) {
				Field f = fieldsList.get(i);
				if (i>0)
					queryWriter.append(',');
				queryWriter.append(':');
				queryWriter.append(f.getName());
//				Object value = getParameters().get(f.getName());
//				
			}
			queryWriter.append(")");
			
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
