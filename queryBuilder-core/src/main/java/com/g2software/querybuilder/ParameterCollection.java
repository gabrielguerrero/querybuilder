package com.g2software.querybuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ParameterCollection {
	private Map parameters = new HashMap();

	public ParameterCollection add(String name, Object value) {
		parameters.put(name, value);
		return this;
	}

	public ParameterCollection clear() {
		parameters.clear();
		return this;
	}

	public Object get(String name) {
		return parameters.get(name);
	}

	public Map getParameters() {
		return parameters;
	}

	public boolean isEmpty() {
		return parameters.isEmpty();
	}

	public Set keySet() {
		return parameters.keySet();
	}

	public Object remove(Object key) {
		return parameters.remove(key);
	}

	public ParameterCollection setParameters(Map variables) {
		this.parameters = variables;
		return this;
	}

	public int size() {
		return parameters.size();
	}

	@Override
	public String toString() {
		return parameters.toString();
	}

}
