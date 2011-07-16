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

import javax.persistence.EntityManager;

import org.hibernate.Session;

import com.querybuilder.SelectQueryBuilder;
import com.querybuilder.transformer.QueryResultTransformer;

public class HqlSelectQueryBuilder extends SelectQueryBuilder<HqlSelectQueryBuilder,HqlExecutor> {

	private boolean removeFetchKeyWord;
	private QueryResultTransformer resultTransformer;
	
	public HqlSelectQueryBuilder(Session session) {
		this(new HqlExecutor(session));
	}
	public HqlSelectQueryBuilder(EntityManager em) {
		this(new HqlExecutor((Session) em.getDelegate()));
	}
	protected HqlSelectQueryBuilder(HqlExecutor executor) {
		super(executor);
	}

	public HqlSelectQueryBuilder setResultTransformer(
			QueryResultTransformer defaultResultTransformer) {
		this.resultTransformer = defaultResultTransformer;
		return  this;
	}
	
	public QueryResultTransformer getResultTransformer() {
		return resultTransformer;
	}
	
	public void setRemoveFetchKeyWord(boolean removeFetchKeyWord) {
		this.removeFetchKeyWord = removeFetchKeyWord;
	}
	public boolean isFetchKeyWordRemoved() {
		return removeFetchKeyWord;
	}
	
	@Override
	protected void setBuiltQuery(String query) {
		if (removeFetchKeyWord && query!=null){
			super.setBuiltQuery(query.replaceAll("\\bfetch\\b", ""));
		} else
			super.setBuiltQuery(query);
	}
	
	@Override
	protected HqlSelectQueryBuilder newQueryBuilder(){
		return new HqlSelectQueryBuilder(getQueryExecutor());
	}
	
	@Override
	public Object clone() {
		HqlSelectQueryBuilder queryBuilder = (HqlSelectQueryBuilder) super.clone();
		queryBuilder.setResultTransformer(resultTransformer);
		return queryBuilder;
	}
}
