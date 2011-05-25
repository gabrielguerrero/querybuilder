package com.g2software.querybuilder;

import java.util.LinkedList;

public class FieldsCollection<T extends QueryBuilder> {
	T parentStatement;
	private LinkedList<Field> fields = new LinkedList<Field>();

	@SuppressWarnings("unchecked")
	FieldsCollection(QueryBuilder parentStatement) {
		this.parentStatement = (T) parentStatement;
	}

	public FieldsCollection<T> add(Field field) {
		if (!this.fields.contains(field))
			this.fields.add(field);
		return this;
	}

	public FieldsCollection<T> add(int position, Field field) {
		if (!this.fields.contains(field))
			this.fields.add(position, field);
		return this;
	}

	public FieldsCollection<T> add(String fieldName) {
		Field field = new Field(fieldName);
		if (!this.fields.contains(field))
			this.fields.add(field);
		return this;
	}

	public FieldsCollection<T> addAfter(Field statement,
			Field afterThisStatement) {
		int position = this.fields.indexOf(afterThisStatement);
		add(position + 1, statement);
		return this;
	}

	public FieldsCollection<T> addAll(FieldsCollection<T> statement) {
		fields.addAll(statement.getFieldsList());
		return this;
	}

	public FieldsCollection<T> addBefore(Field statement,
			Field beforeThisStatement) {
		int position = this.fields.indexOf(beforeThisStatement);
		if (position > -1)
			add(position, statement);
		else
			addLast(statement);
		return this;
	}

	public FieldsCollection<T> addLast(Field statement) {
		if (!this.fields.contains(statement))
			this.fields.addLast(statement);
		return this;
	}

	public FieldsCollection<T> clear() {
		this.fields.clear();
		return this;
	}

	public T end() {
		return parentStatement;
	}

	public LinkedList<Field> getFieldsList() {
		return this.fields;
	}

	protected FieldsCollection<T> joinBy(char character, StringBuilder sb) {
		if (!this.fields.isEmpty()) {
			for (int i = 0; i < this.fields.size(); i++) {
				if (i > 0)
					sb.append(character);
				sb.append(this.fields.get(i)
					.toString());
			}
		}
		return this;
	}

	public FieldsCollection<T> remove(Field statement) {
		this.fields.remove(statement);
		return this;
	}

	public FieldsCollection<T> remove(int position) {
		this.fields.remove(position);
		return this;
	}

	public FieldsCollection<T> replace(Field newStatement, Field oldStatement) {
		int position = this.fields.indexOf(oldStatement);
		remove(position);
		add(position, newStatement);
		return this;
	}

	public int size() {
		return fields.size();
	}

}
