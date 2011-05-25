package com.g2software.querybuilder.transformer;

public interface AddResultFromArray extends QueryResultTransformer {
	public Object addResult(Object[] values, String[] aliases);
}
