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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({ "unchecked","rawtypes" })
public abstract class UpdateQueryBuilder<E extends UpdateQueryBuilder<?, ?>, T extends QueryExecutor>
		extends QueryBuilder<E, T> {

	private String table;
	
	private FieldsCollection<E> fields = new FieldsCollection<E>(this);
	private ConditionsClause<E> conditions = new ConditionsClause<E>(this);
	
	private SelectQueryBuilder<SelectQueryBuilder<?,?>, T> valuesFrom;
	
	public UpdateQueryBuilder(T queryExecutor) {
		super(queryExecutor);
	}

	/**
	 * Sets the table to be used in the update query
	 * e.g.:
	 * <pre>
	 *  SqlUpdateQueryBuilder updateQuery = queryFactory.newUpdateQueryBuilder();
     *  updateQuery.update("Customer c")
     *                  .setFieldValue("FIRSTNAME", "Gabriel")
     *                  .setFieldValue("LASTNAME", "Guerrero")
     *                  .where("c.firstName like :name")
     *                  .setParameter("name", "Andrew");
     * </pre>                 
	 * @param table
	 * @return
	 */
	public E update(String table) {
		queryChanged();
		this.table = table;
		return (E) this;
	}
	
	public String getUpdateTable() {
		return table;
	}
	
	/**
	 * Returns the conditions collection for the "where" clause, usually needed to add/remove/change a condition of the "where" clause
	 * e.g.: <pre>query.where().addAnd("p.salary = :salary").addOr("p.startDate >= :startDate")</pre>
	 * @return
	 */
	public ConditionsClause<E> where() {
		queryChanged();
		return conditions;
	}

	/**
	 * Clears the where conditions collection and adds the arguments, by default the arguments will be join using an "and" condition, to get control if they are "and" or "or" use where()
	 * @param conditions
	 * @return queryBuilder
	 */
	public E where(String... conditions) {
		where().clear();
		for (int i = 0; i < conditions.length; i++) {
			where().addAnd(conditions[i]);
		}
		return where().end();
	}
	
	/**
	 * Builds the query, after this you can call getBuiltQuery() to check the built query, or execute() to run the query
	 */
	@Override
	public E build() {

		StringBuilder queryWriter = new StringBuilder("update "+getUpdateTable());

		queryWriter.append(" set ");
		LinkedList<Field> fieldsList = fields.getFieldsList();
		Map newParameters = new HashMap();
		for (int i = 0;i<fields().size();i++) {
			Field f = fieldsList.get(i);
			Object value = getParameters().get(f.getName());
			if (i>0)
				queryWriter.append(',');
			queryWriter.append(f.getName());
			if (value instanceof SelectQueryBuilder){
				SelectQueryBuilder subquery = (SelectQueryBuilder) value;
				subquery.build();
				String sql = subquery.getBuiltQuery();
				queryWriter.append(" = (");
				queryWriter.append(sql);
				queryWriter.append(") ");
				newParameters.putAll(subquery.getParameters().getParameters());
			} else {
				queryWriter.append(" = :");
				queryWriter.append(f.getName());
			}
			
		}
		
		if (!where().isEmpty()) {
			queryWriter.append(" where ");
			queryWriter.append(where().toString());
		}
		
		setBuiltQuery(queryWriter.toString());
		
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
	 * Sets the field and the value to the insert statement
	 * @param name
	 * @param value
	 * @return
	 */
	public E setFieldValue(String name, Object value){
		queryChanged();
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

	@SuppressWarnings("unchecked")
	private void setFields(FieldsCollection<?> fields) {
		this.fields = (FieldsCollection<E>) fields;
	}

}
