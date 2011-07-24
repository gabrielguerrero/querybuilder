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
package com.querybuilder;

import java.util.LinkedList;

@SuppressWarnings({ "unchecked","rawtypes" })
public abstract class InsertQueryBuilder<E extends InsertQueryBuilder<?, ?>, T extends QueryExecutor>
		extends QueryBuilder<E, T> {

	private String table;
	
	private FieldsCollection<E> fields = new FieldsCollection<E>(this);

	private SelectQueryBuilder<SelectQueryBuilder<?,?>, T> valuesFrom;
	
	public InsertQueryBuilder(T queryExecutor) {
		super(queryExecutor);
	}

	/**
	 * Sets the <code>table</code> where the values will be inserted
	 * e.g.:
	 * <pre>
	 * SqlInsertQueryBuilder insertQuery = queryFactory.newInsertQueryBuilder();
     * 
     * insertQuery.insertInto("CustomerBackUp")
     * .setFieldValue("ID", 55)
     * .setFieldValue("FIRSTNAME", "Gabriel")
     * .setFieldValue("LASTNAME", "Guerrero")
     * .build();
	 * </pre>
	 * @param table
	 * @return
	 */
	public E insertInto(String table) {
		queryChanged();
		this.table = table;
		return (E) this;
	}
	
	/**
	 * Sets the query that will be used for a batch insert
	 * e.g.:
	 * <pre>
	 * selectQuery.select("c.id, c.firstName, c.lastName").from("Customer c");
     * 
     * insertQuery.insertInto("Customer").fields("ID","FIRSTNAME","LASTNAME").setValuesFrom(selectQuery);
     * 
     * insertQuery.build();
	 * </pre>
	 * @param valuesFrom
	 * @return
	 */
	public E setValuesFrom(SelectQueryBuilder valuesFrom){
		queryChanged();
		this.valuesFrom = valuesFrom;
		return (E) this;
	}
	
	public String getInsertTable() {
		return table;
	}
	/**
	 * Builds the query, after this you can call getBuiltQuery() to check the built query, or execute() to run the query
	 */
	@Override
	public E build() {

		StringBuilder queryWriter = new StringBuilder("insert into "+getInsertTable());
		queryWriter.append(" (");

		fields().joinBy(',', queryWriter);

		queryWriter.append(")");

		if (valuesFrom!=null){
			String selectQuery = valuesFrom.build().getBuiltQuery();
			queryWriter.append(" (");
			queryWriter.append(selectQuery);
			queryWriter.append(")");
			getParameters().getParameters().putAll(valuesFrom.getParameters().getParameters());
		} else {
			queryWriter.append(" values (");
			
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


	/**
	 * Returns the fields collection for the "fields" clause, usually needed to add/remove/change a field of "fields" clause
	 * eg: <pre>query.fields().remove("id")</pre>
	 * @return
	 */
	public FieldsCollection<E> fields() {
		queryChanged();
		return fields;
	}

	/**
	 * Clears the select fields collection and adds each of the arguments, this is mainly used for batch inserts
	 * e.g.:
	 * <pre>
	 * selectQuery.select("c.id, c.firstName, c.lastName").from("Customer c");
     * 
     * insertQuery.insertInto("Customer").fields("ID","FIRSTNAME","LASTNAME").setValuesFrom(selectQuery);
     * 
     * insertQuery.build();
	 * </pre>
	 * @param fields
	 * @return the queryBuilder, useful for method chaining
	 */
	public E fields(String... fields) {
		fields().clear();
		for (int i = 0; i < fields.length; i++) {
			fields().add(fields[i]);
		}
		return fields().end();
	}

	/**
	 * Sets the field and the value to the insert statement
	 * @param name
	 * @param value
	 * @return
	 */
	public E setFieldValue(String name, Object value){
		fields().add(name);
		setParameter(name, value);
		return (E) this;
	}

	/**
	 * Sets the parameter, if the parameter already exists it will be replaced, no error will be thrown if the parameter is not use in the query
	 * the parameter should appear in the query like :parameterName, they will be replace by ? before the execution
	 * @param name of the named parameter in the query
	 * @param value any basic java type (String, Integer, Date, ...etc) or a collection or another SelectQueryBuilder (for subquery)
	 * @return
	 */
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

	private void setFields(FieldsCollection<?> fields) {
		this.fields = (FieldsCollection<E>) fields;
	}

}
