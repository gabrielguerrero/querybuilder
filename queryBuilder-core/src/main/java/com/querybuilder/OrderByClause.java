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

public class OrderByClause<T extends QueryBuilder> extends StatementClause<T>{

	OrderByClause(QueryBuilder parentStatement) {
		super(parentStatement);
	}
	
	public OrderByClause<T> add(String field,boolean ascending){
		add(field+ (ascending ? " asc" :" desc"));
		return this;
	}
	public OrderByClause<T> remove(String field,boolean ascending){
		remove(field+ (ascending ? " asc" :" desc"));
		return this;
	}
	public OrderByClause<T> addAsc(String field){
		add(field,true);
		return this;
	}
	public OrderByClause<T> removeAsc(String field){
		remove(field,true);
		return this;
	}
	public OrderByClause<T> addDesc(String field){
		add(field,false);
		return this;
	}
	public OrderByClause<T> removeDesc(String field){
		remove(field,false);
		return this;
	}

}
