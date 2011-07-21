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

public class ConditionsClause<T extends QueryBuilder> {
	private static final String OR_STR = " or ";
	private static final String AND_STR = " and ";
	private LinkedList<QueryCondition> conditions = new LinkedList<QueryCondition>();
	private T parentStatement;

	@SuppressWarnings("unchecked")
	public ConditionsClause(QueryBuilder statement) {
		this.parentStatement = (T) statement;
	}

	public ConditionsClause<T> addAll(ConditionsClause<T> whereClause) {
		this.conditions.addAll(whereClause.getConditions());
		return this;
	}

	public ConditionsClause<T> addAnd(String condition) {
		if (condition != null)
			conditions.add(QueryCondition.newAndCondition(condition));
		return this;
	}

	public ConditionsClause<T> addCondition(QueryCondition condition) {
		if (condition != null && !condition.isEmpty())
			conditions.add(condition);
		return this;
	}

	public ConditionsClause<T> addOr(String condition) {
		if (condition != null)
			conditions.add(QueryCondition.newOrCondition(condition));
		return this;
	}

	public T end() {
		return parentStatement;
	}

	protected LinkedList<QueryCondition> getConditions() {
		return conditions;
	}

	public boolean isEmpty() {
		return conditions.isEmpty();
	}

	public ConditionsClause<T> clear() {
		conditions.clear();
		return this;
	}

	public ConditionsClause<T> removeAndCondition(String condition) {
		return removeCondition(QueryCondition.newAndCondition(condition));
	}

	public ConditionsClause<T> removeCondition(QueryCondition condition) {
		if (condition != null && !condition.isEmpty())
			conditions.remove(condition);
		return this;
	}

	public ConditionsClause<T> removeOrCondition(String condition) {
		return removeCondition(QueryCondition.newOrCondition(condition));
	}

	protected ConditionsClause<T> setConditions(LinkedList<QueryCondition> conditions) {
		this.conditions = conditions;
		return this;
	}

	public int size() {
		return conditions.size();
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < conditions.size(); i++) {
			QueryCondition condition = conditions.get(i);
			if (i > 0) {
				if (condition.getType() == QueryCondition.Type.AND)
					str.append(AND_STR);
				else
					str.append(OR_STR);
			}
			str.append('(');
			str.append(condition.toString());
			str.append(')');
		}
		return str.toString();
	}
}
