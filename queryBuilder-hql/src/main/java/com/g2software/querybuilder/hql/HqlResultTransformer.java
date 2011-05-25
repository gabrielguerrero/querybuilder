package com.g2software.querybuilder.hql;

import org.hibernate.transform.ResultTransformer;

import com.g2software.querybuilder.transformer.QueryResultTransformer;

public interface HqlResultTransformer extends QueryResultTransformer{

    public abstract ResultTransformer getHibernateResultTransformer();

}