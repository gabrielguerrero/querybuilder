package com.g2software.querybuilder.transformer;

import java.util.Map;

public interface AddResultFromMap extends QueryResultTransformer {
	public Object addResult(Map row);
}
