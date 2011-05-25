package com.g2software.querybuilder.hql;

import org.hibernate.transform.ResultTransformer;

import com.g2software.querybuilder.transformer.QueryResultTransformer;

public class HqlResultTransformerWrapper implements QueryResultTransformer, HqlResultTransformer{
    ResultTransformer resultTransformer;

    public HqlResultTransformerWrapper(ResultTransformer transformer) {
        super();
        this.resultTransformer = transformer;
    }

    /* (non-Javadoc)
     * @see com.g2software.util.db.HqlResultTransformer2#getHibernateResultTransformer()
     */
    public ResultTransformer getHibernateResultTransformer() {
        return resultTransformer;
    }
}
