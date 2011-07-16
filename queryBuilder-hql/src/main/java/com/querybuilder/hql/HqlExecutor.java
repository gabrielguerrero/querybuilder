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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;

import com.querybuilder.QueryBuilder;
import com.querybuilder.QueryBuilderException;
import com.querybuilder.QueryExecutor;
import com.querybuilder.transformer.AddResultFromArray;
import com.querybuilder.transformer.AddResultFromMap;
import com.querybuilder.transformer.QueryResultTransformer;
import com.querybuilder.transformer.ReturnResult;

public class HqlExecutor extends QueryExecutor {

	private static class HB3ResultTransformerForArrays implements
			ResultTransformer, AddResultFromArray {
		AddResultFromArray resultTransformer;

		public HB3ResultTransformerForArrays(
				AddResultFromArray resultTransformer) {
			this.resultTransformer = resultTransformer;
		}

		public Object addResult(Object[] values, String[] aliases) {
			return resultTransformer.addResult(values, aliases);
		}

		public List transformList(List collection) {
			return collection;
		}

		public Object transformTuple(Object[] tuple, String[] aliases) {
			return addResult(tuple, aliases);
		}

	}

	private static class HB3ResultTransformerForMaps implements
			ResultTransformer, AddResultFromMap {
		AddResultFromMap resultTransformer;

		public HB3ResultTransformerForMaps(AddResultFromMap resultTransformer) {
			this.resultTransformer = resultTransformer;
		}

		@SuppressWarnings("unchecked")
		public Object addResult(Map row) {
			return resultTransformer.addResult(row);
		}

		@SuppressWarnings("unchecked")
		public List transformList(List collection) {
			return collection;
		}

		@SuppressWarnings("unchecked")
		public Object transformTuple(Object[] tuple, String[] aliases) {
			Map row = new HashMap(tuple.length + ((int) (tuple.length * 0.25)));
			for (int i = 0; i < aliases.length; i++) {
				String alias = aliases[i];
				if (alias == null)
					alias = "field" + i;
				row.put(alias, tuple[i]);
			}
			return addResult(row);
		}
	}

	private QueryResultTransformer resultTransformer;

	private Query query;

	private Session session;

	public HqlExecutor(Session session) {
		this.session = session;
	}

	@Override
	public void close() {
		if (getSession()!=null)
			getSession().close();
	}

	public Query getQuery() {
		return query;
	}

	@Override
	public HqlSelectQueryBuilder getQueryBuilder() {
		return (HqlSelectQueryBuilder) super.getQueryBuilder();
	}

	public Object getResult() {
		if (resultTransformer == null
				|| !(resultTransformer instanceof ReturnResult))
			return query.list();
		query.list();
		return ((ReturnResult) resultTransformer).returnResult();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getResultList() {
		Object result = getResult();
		if (!(result instanceof List))
			throw new QueryBuilderException("The QueryResultTransformer used does not return a List, use getResults() and cast it to the appropiate type ");
		return (List<T>) result;
	}

	@SuppressWarnings("unchecked")
	public <K,V> Map<K,V> getResultMap() {
		if (this.getQueryBuilder()
			.getResultTransformer() == null) {
			query.setResultTransformer(new HB3ResultTransformerForArrays(new com.querybuilder.transformer.MapResultTransformer(1000)));
		}
		query.list();
		Object resultMap = ((ReturnResult) resultTransformer).returnResult();
		if (resultMap instanceof Map)
			throw new UnsupportedOperationException("The result transforme does not support the map result operation");

		resultTransformer = null;
		return (Map<K,V>) resultMap;
	}

	public Session getSession() {
		return session;
	}

	public <T> T getUniqueResult() {
		return (T) query.uniqueResult();
	}

	@Override
	protected void init(QueryBuilder queryBuilder) {
		super.init(queryBuilder);
		String queryString = queryBuilder.getBuiltQuery();
		query = getSession().createQuery(queryString);
		if (!queryBuilder.getParameters()
			.isEmpty()) {
			for (Iterator iter = queryBuilder.getParameters()
				.keySet()
				.iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				if (queryBuilder.getBuiltQuery()
					.indexOf(":" + key) != -1) {
					Object value = queryBuilder.getParameters()
						.get(key);
					query.setParameter(key, value);
				}
			}
		}
		HqlSelectQueryBuilder selectQueryBuilder = (HqlSelectQueryBuilder) queryBuilder;
		resultTransformer = selectQueryBuilder.getResultTransformer();
		if (selectQueryBuilder.getFirstResults() > -1)
			query.setFirstResult(selectQueryBuilder.getFirstResults());
		if (selectQueryBuilder.getMaxResults() > -1)
			query.setMaxResults(selectQueryBuilder.getMaxResults());
		if (selectQueryBuilder.getResultTransformer() != null) {
			if (resultTransformer instanceof AddResultFromArray)
				query.setResultTransformer(new HB3ResultTransformerForArrays((AddResultFromArray) resultTransformer));
			else if (resultTransformer instanceof AddResultFromMap)
				query.setResultTransformer(new HB3ResultTransformerForMaps((AddResultFromMap) resultTransformer));
			else
				throw new QueryBuilderException("Unsupported transformer");
		}
	}

	public HqlExecutor setSession(Session session) {
		this.session = session;
		return this;
	}
	
}
