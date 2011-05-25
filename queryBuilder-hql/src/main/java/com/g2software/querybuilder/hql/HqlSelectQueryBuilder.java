package com.g2software.querybuilder.hql;

import javax.persistence.EntityManager;

import org.hibernate.Session;

import com.g2software.querybuilder.SelectQueryBuilder;
import com.g2software.querybuilder.transformer.QueryResultTransformer;

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
