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
package com.querybuilder.sql;

import java.sql.Connection;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.querybuilder.SelectQueryBuilder;
import com.querybuilder.UpdateQueryBuilder;

public class SqlUpdateQueryBuilder extends UpdateQueryBuilder<SqlUpdateQueryBuilder, SQLExecutor> {

	@SuppressWarnings("rawtypes")
	
	public SqlUpdateQueryBuilder(SQLExecutor queryExecutor) {
		super(queryExecutor);
	}
	
	public SqlUpdateQueryBuilder(Connection connection) {
		this(new SQLExecutor(connection));
	}

	@Override
	protected SqlUpdateQueryBuilder newQueryBuilder() {
		return null;
	}
	
	@Override
	public Object clone() {
		SqlUpdateQueryBuilder queryBuilder = (SqlUpdateQueryBuilder) super.clone();
		return queryBuilder;
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SqlUpdateQueryBuilder build() {
		super.build();
		String sql = getBuiltQuery();
		Set<Map.Entry> parameters2 = getParameters().getParameters().entrySet();
		for (Map.Entry entry : parameters2) {
			if (entry.getValue() instanceof SqlUpdateQueryBuilder){
				SelectQueryBuilder subquery = (SelectQueryBuilder) entry.getValue();
				subquery.build();
				String subsql = subquery.getBuiltQuery();
				StringBuilder builder = new StringBuilder('(');
				builder.append(subsql);
				builder.append(')');
				sql.replaceAll("\\:"+entry.getKey(), builder.toString());
				getParameters().getParameters().putAll(subquery.getParameters().getParameters());
			} 
//			else	if (entry.getValue() instanceof Collection){
//					Collection c = (Collection) entry.getValue();
//					StringBuilder builder = new StringBuilder("(");
//					for (Object object : c) {
//						if (object instanceof String){
//							builder.append("'");
//							builder.append(object);
//							builder.append("'");
//						} else {
//							builder.append(object);
//						}
//					}
//					builder.append(")");
//					sql.replaceAll("\\:"+entry.getKey(), builder.toString());
//			}
		}
		setBuiltQuery(sql);
		return this;
	}
}
