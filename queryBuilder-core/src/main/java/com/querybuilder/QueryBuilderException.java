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

public class QueryBuilderException extends RuntimeException {

	public QueryBuilderException(QueryBuilder queryBuilder, Throwable t) {
		super(queryBuilder.getBuiltQuery() + '\n'
				+ queryBuilder.getParameters(), t);
	}

	public QueryBuilderException(String message) {
		super(message);
	}

	public QueryBuilderException(String message, Throwable t) {
		super(message, t);
	}

}