package com.g2software.querybuilder.hql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.transform.ResultTransformer;

public class HqlMapResultTransformer implements HqlResultTransformer,ResultTransformer {

    private Map resultMap;
    public HqlMapResultTransformer(Map resultMap) {
        this.resultMap = resultMap;
    }
    public ResultTransformer getHibernateResultTransformer() {
        return this;
    }

    public List transformList(List collection) {
        return collection;
    }

    public Object transformTuple(Object[] tuple, String[] aliases) {
        if (tuple.length==1) {
            resultMap.put(tuple[0], Boolean.TRUE);
            return tuple[0];
        }
        Map result = new HashMap(tuple.length);
        for ( int i=1; i<tuple.length; i++ ) {
                String alias = aliases[i];
                 if ( alias!=null ) {
                         result.put( alias, tuple[i] );
                 }
         }
        resultMap.put(tuple[0],result);
        return tuple[0];
    }
    public void setResultMap(Map resultMap) {
        this.resultMap = resultMap;
    }
    /* (non-Javadoc)
     * @see com.g2software.util.db.MapResultTrasformer#getResultMap()
     */
    public Map getResultMap() {
        return resultMap;
    }


}
