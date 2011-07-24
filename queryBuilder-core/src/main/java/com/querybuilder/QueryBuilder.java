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


public abstract class QueryBuilder<E extends QueryBuilder<?,?>, T extends QueryExecutor> {

	private String query;
	private ParameterCollection parameters = new ParameterCollection();
	private T queryExecutor;
	public QueryBuilder(T queryExecutor) {
		this.queryExecutor = queryExecutor;
	}

	/**
	 * executes the query
	 * @return
	 */
	public T execute() {
		if (getBuiltQuery()==null){
			build();
		}
		queryExecutor.init(this);
		return queryExecutor;
	}

	public E build()  {
		return (E) this;
	}

	/**
	 * returns the build query as a string
	 * @return
	 */
	public String getBuiltQuery() {
		return query;
	}

	public ParameterCollection getParameters() {
		return parameters;
	}

	protected final T getQueryExecutor() {
		return queryExecutor;
	}

	protected void setBuiltQuery(String query) {
		this.query = query;
	}
	
	protected final void setParameters(ParameterCollection parameters) {
		this.parameters = parameters;
	}

	protected void queryChanged(){
		if (getBuiltQuery()!=null)
			setBuiltQuery(null);
	}
}
