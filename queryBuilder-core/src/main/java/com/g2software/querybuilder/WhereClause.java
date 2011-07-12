package com.g2software.querybuilder;

import java.util.LinkedList;

public class WhereClause<T extends QueryBuilder> {
	private static final String OR_STR = " or ";
	private static final String AND_STR = " and ";
	private LinkedList<QueryCondition> conditions = new LinkedList<QueryCondition>();
	private T parentStatement;

	@SuppressWarnings("unchecked")
	public WhereClause(QueryBuilder statement) {
		this.parentStatement = (T) statement;
	}

	public WhereClause<T> addAll(WhereClause<T> whereClause) {
		this.conditions.addAll(whereClause.getConditions());
		return this;
	}

	public WhereClause<T> addAnd(String condition) {
		if (condition != null)
			conditions.add(QueryCondition.newAndCondition(condition));
		return this;
	}

	public WhereClause<T> addCondition(QueryCondition condition) {
		if (condition != null && !condition.isEmpty())
			conditions.add(condition);
		return this;
	}

	public WhereClause<T> addOr(String condition) {
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

	public WhereClause<T> clear() {
		conditions.clear();
		return this;
	}

	public WhereClause<T> removeAndCondition(String condition) {
		return removeCondition(QueryCondition.newAndCondition(condition));
	}

	public WhereClause<T> removeCondition(QueryCondition condition) {
		if (condition != null && !condition.isEmpty())
			conditions.remove(condition);
		return this;
	}

	public WhereClause<T> removeOrCondition(String condition) {
		return removeCondition(QueryCondition.newOrCondition(condition));
	}

	protected WhereClause<T> setConditions(LinkedList<QueryCondition> conditions) {
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
