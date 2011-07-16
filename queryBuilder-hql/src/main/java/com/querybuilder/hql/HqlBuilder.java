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
package com.querybuilder.hql;

import org.hibernate.Session;

import com.querybuilder.FieldsCollection;
import com.querybuilder.StatementClause;

public class HqlBuilder {

	private static HqlSessionProvider sessionProvider;

	public static HqlSelectQueryBuilder createSelect() {
		Session session = sessionProvider.getCurrentSession();
		HqlSelectQueryBuilder builder = new HqlSelectQueryBuilder(session);
		return builder;
	}

	public static FieldsCollection<HqlSelectQueryBuilder> select() {
		return createSelect().select();
	}

	public static HqlSelectQueryBuilder select(String fields) {
		return createSelect().select(fields);
	}

	public static void setSessionProvider(HqlSessionProvider sessionProvider) {
		HqlBuilder.sessionProvider = sessionProvider;
	}
}
