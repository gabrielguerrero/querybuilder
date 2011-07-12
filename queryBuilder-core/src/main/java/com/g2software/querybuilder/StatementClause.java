package com.g2software.querybuilder;

import java.util.LinkedList;

public class StatementClause<T extends QueryBuilder> {
	T parentStatement;
	private LinkedList<String> statements = new LinkedList<String>();

	@SuppressWarnings("unchecked")
	StatementClause(QueryBuilder parentStatement) {
		this.parentStatement = (T) parentStatement;
	}

	public StatementClause<T> add(int position, String statement) {
		statement = statement.trim();
		if (!this.statements.contains(statement))
			this.statements.add(position, statement);
		return this;
	}

	public StatementClause<T> add(String statement) {
		statement = statement.trim();
		if (!this.statements.contains(statement))
			this.statements.add(statement);
		return this;
	}

	public StatementClause<T> addAfter(String statement,
			String afterThisStatement) {
		afterThisStatement = afterThisStatement.trim();
		int position = this.statements.indexOf(afterThisStatement);
		add(position + 1, statement);
		return this;
	}

	public StatementClause<T> addAll(StatementClause<T> statement) {
		statements.addAll(statement.getStatements());
		return this;
	}

	public StatementClause<T> addBefore(String statement,
			String beforeThisStatement) {
		beforeThisStatement = beforeThisStatement.trim();
		int position = this.statements.indexOf(beforeThisStatement);
		if (position > -1)
			add(position, statement);
		else
			addLast(statement);
		return this;
	}

	public StatementClause<T> addLast(String statement) {
		statement = statement.trim();
		if (!this.statements.contains(statement))
			this.statements.addLast(statement);
		return this;
	}

	public StatementClause<T> clear() {
		this.statements.clear();
		return this;
	}
	public boolean isEmpty(){
		return this.statements.isEmpty();
	}

	public T end() {
		return parentStatement;
	}

	protected LinkedList<String> getStatements() {
		return this.statements;
	}

	public StatementClause<T> joinBy(char character, StringBuilder sb) {
		if (!this.statements.isEmpty()) {
			for (int i = 0; i < this.statements.size(); i++) {
				if (i > 0)
					sb.append(character);
				sb.append(this.statements.get(i)
					.toString());
			}
		}
		return this;
	}

	public StatementClause<T> remove(int position) {
		this.statements.remove(position);
		return this;
	}

	public StatementClause<T> remove(String statement) {
		statement = statement.trim();
		this.statements.remove(statement);
		return this;
	}

	public StatementClause<T> replace(String newStatement, String oldStatement) {
		oldStatement = oldStatement.trim();
		int position = this.statements.indexOf(oldStatement);
		if (position==-1)
			throw new QueryBuilderException("The statement to replace is not found. oldStatement:"+oldStatement);
		remove(position);
		add(position, newStatement);
		return this;
	}

	protected StatementClause<T> setStatements(LinkedList joinTables) {
		this.statements = joinTables;
		return this;
	}

	public int size() {
		return statements.size();
	}

}
