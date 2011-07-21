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


@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class SelectQueryBuilder<E extends SelectQueryBuilder<?, ?>, T extends QueryExecutor>
		extends QueryBuilder<E, T> {

	private int firstResults = -1;

	private int maxResults = -1;
	private ConditionsClause<E> whereConditions = new ConditionsClause<E>(this);
	private ConditionsClause<E> havingConditions = new ConditionsClause<E>(this);
	private FieldsCollection<E> fields = new FieldsCollection<E>(this);
	private StatementClause<E> groupByFields = new StatementClause<E>(this);
	private OrderByClause<E> orderByFields = new OrderByClause<E>(this);
	private StatementClause<E> joins = new StatementClause<E>(this);

	public SelectQueryBuilder(T queryExecutor) {
		super(queryExecutor);
		select("*");
	}

	@Override
	public E build() {

		StringBuilder queryWriter = new StringBuilder(select().size() * 20
				+ select().size() * 30 + where().size() * 30 + 40);
		queryWriter.append("select ");

		select().joinBy(',', queryWriter);

		queryWriter.append(" from ");

		from().joinBy(' ', queryWriter);

		if (!where().isEmpty()) {
			queryWriter.append(" where ");
			queryWriter.append(where().toString());
		}
		if (!groupBy().isEmpty()) {
			queryWriter.append(" group by ");
			groupBy().joinBy(',', queryWriter);
		}
		if (!having().isEmpty()) {
			queryWriter.append(" having ");
			queryWriter.append(having().toString());
		}
		if (!orderBy().isEmpty()) {
			queryWriter.append(" order by ");
			orderBy().joinBy(',', queryWriter);
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

	public E clearFirstAndMaxResultLimits() {
		queryChanged();
		setMaxResults(-1);
		setFirstResults(-1);
		return (E) this;
	}

	public StatementClause<E> from() {
		queryChanged();
		return joins;
	}

	public E from(String from,String... joins) {
		from().clear().add(from);
		if (joins!=null)
			for (int i = 0; i < joins.length; i++) {
				from().add(joins[i]);
			}
		return from().end();
	}

	public int getFirstResults() {
		return firstResults;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public StatementClause<E> groupBy() {
		queryChanged();
		return groupByFields;
	}

	public E groupBy(String... groupBy) {
		groupBy().clear();
		for (int i = 0; i < groupBy.length; i++) {
			groupBy().add(groupBy[i]);
		}
		return groupBy().end();
	}

	public OrderByClause<E> orderBy() {
		queryChanged();
		return orderByFields;
	}

	public E orderBy(String... orderBy) {
		orderBy().clear();
		for (int i = 0; i < orderBy.length; i++) {
			orderBy().add(orderBy[i]);
		}
		return orderBy().end();
	}
	
	public E orderBy(String orderBy,boolean ascending) {
		orderBy().clear();
		orderBy().add(orderBy,ascending);
		return orderBy().end();
	}

	public FieldsCollection<E> select() {
		queryChanged();
		return fields;
	}

	public E select(String... fields) {
		select().clear();
		for (int i = 0; i < fields.length; i++) {
			select().add(fields[i]);
		}
		return select().end();
	}

	public E setFirstResults(int firstResult) {
		queryChanged();
		this.firstResults = firstResult;
		return (E) this;
	}

	public E setMaxResults(int maxResult) {
		queryChanged();
		this.maxResults = maxResult;
		return (E) this;
	}

	public E setParameter(String name, Object value) {
		getParameters().add(name, value);
		return (E) this;
	}

	public ConditionsClause<E> where() {
		queryChanged();
		return whereConditions;
	}

	public E where(String... conditions) {
		where().clear();
		for (int i = 0; i < conditions.length; i++) {
			where().addAnd(conditions[i]);
		}
		return where().end();
	}
	
	public ConditionsClause<E> having() {
		queryChanged();
		return havingConditions;
	}
	
	public E having(String... conditions) {
		having().clear();
		for (int i = 0; i < conditions.length; i++) {
			having().addAnd(conditions[i]);
		}
		return having().end();
	}

	@Override
	public String toString() {
		return "query: " + getBuiltQuery() + " parameters: " + getParameters();
	}

	protected abstract E newQueryBuilder();

	public Object clone() {
		E queryBuilder = newQueryBuilder();
		queryBuilder.setFields(fields);
		queryBuilder.setJoins(joins);
		queryBuilder.setConditions(whereConditions);
		queryBuilder.setGroupByFields(groupByFields);
		queryBuilder.setOrderByFields(orderByFields);
		queryBuilder.setParameters(getParameters());
		queryBuilder.setFirstResults(firstResults);
		queryBuilder.setMaxResults(maxResults);
//		queryBuilder.setResultTransformer(resultTransformer);
		return queryBuilder;
	}

	private void setConditions(ConditionsClause<?> conditions) {
		this.whereConditions = (ConditionsClause<E>) conditions;
	}

	private void setFields(FieldsCollection<?> fields) {
		this.fields = (FieldsCollection<E>) fields;
	}

	private void setGroupByFields(StatementClause<?> groupByFields) {
		this.groupByFields = (StatementClause<E>) groupByFields;
	}

	protected final void setOrderByFields(OrderByClause<?> orderByFields) {
		this.orderByFields = (OrderByClause<E>) orderByFields;
	}

	private void setJoins(StatementClause<?> joins) {
		this.joins = (StatementClause<E>) joins;
	}
}
