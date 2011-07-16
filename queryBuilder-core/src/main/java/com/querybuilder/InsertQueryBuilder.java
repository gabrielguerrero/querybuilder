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

	public InsertQueryBuilder<E,T> insertInto(String table) {
		queryChanged();
		this.table = table;
		return this;
	}
	
	public InsertQueryBuilder<E,T> setValuesFrom(SelectQueryBuilder valuesFrom){
		queryChanged();
		this.valuesFrom = valuesFrom;
		return this;
	}
	
	public String getInsertTable() {
		return table;
	}
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



	public FieldsCollection<E> fields() {
		queryChanged();
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

	private void setFields(FieldsCollection<?> fields) {
		this.fields = (FieldsCollection<E>) fields;
	}

}
