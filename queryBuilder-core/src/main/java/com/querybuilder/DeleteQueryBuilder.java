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
import java.util.Map;
import java.util.Set;

public abstract class DeleteQueryBuilder<E extends DeleteQueryBuilder<?, ?>, T extends QueryExecutor>
		extends QueryBuilder<E, T> {

	private String table;
	
	private ConditionsClause<E> conditions = new ConditionsClause<E>(this);
	
	public DeleteQueryBuilder(T queryExecutor) {
		super(queryExecutor);
	}

	/**
	 * Sets the table that will be deleted
	 * e.g.:
	 * <pre>
	 *  SqlDeleteQueryBuilder deleteQuery = queryFactory.newDeleteQueryBuilder();
 	 *  deleteQuery.deleteFrom("Customer c").where("c.id = :id").setParameter("id", 123).build();
	 * </pre>
	 * @param table
	 * @return
	 */
	public E deleteFrom(String table) {
		queryChanged();
		this.table = table;
		return (E) this;
	}
	
	public String getDeleteTable() {
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
		queryBuilder.setParameters(getParameters());
		return queryBuilder;
	}

}
