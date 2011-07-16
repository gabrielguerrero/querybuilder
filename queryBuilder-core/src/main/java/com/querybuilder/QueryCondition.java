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
package com.querybuilder;
import java.util.ArrayList;
import java.util.List;

public class QueryCondition {
	private List<QueryCondition> conditions = new ArrayList<QueryCondition>();
	private Type type = Type.AND;
	private String condition;
	public enum Type {AND,OR};
	
	public static QueryCondition newAndCondition() {
		return new QueryCondition(Type.AND);
	}
	public static QueryCondition newAndCondition(String condition) {
		return new QueryCondition(condition,Type.AND);
	}
	public static QueryCondition newOrCondition(String condition) {
		return new QueryCondition(condition,Type.OR);
	}
	
	public static QueryCondition newOrCondition() {
		return new QueryCondition(Type.OR);
	}
	
	public QueryCondition(Type type) {
		this.type = type;
	}
	
	private QueryCondition(String condition,Type type) {
		this.type = type;
		this.condition = condition;
	}
	
	public QueryCondition addAnd(String condition) {
		if (condition!=null)
			conditions.add(new QueryCondition(condition,QueryCondition.Type.AND));
		return this;
	}
	public QueryCondition addOr(String condition) {
		if (condition!=null)
			conditions.add(new QueryCondition(condition,QueryCondition.Type.OR));
		return this;
	}
	public QueryCondition addCondition(QueryCondition condition) {
		if (condition!=null && !condition.isEmpty())
			conditions.add(condition);
		return this;
	}
	public QueryCondition removeCondition(QueryCondition condition) {
		if (condition!=null && !condition.isEmpty())
			conditions.remove(condition);
		return this;
	}
	public QueryCondition removeAllConditions() {
		conditions.clear();
		return this;
	}
	public String toString() {
		StringBuffer str = new StringBuffer();
		
		if (condition!=null && conditions.size()>0)
			str.append('(');
			
		if (condition!=null)
			str.append(condition);
		
		if (condition!=null && conditions.size()>0)
			str.append(')');
			
		for (int i = 0; i < conditions.size(); i++) {
			QueryCondition subcondition = (QueryCondition) conditions.get(i);
			if (i>0 || condition!=null) {
				if (subcondition.type == QueryCondition.Type.AND)
					str.append(" and ");
				else
					str.append(" or ");
			} 	
			str.append('(');
			str.append(subcondition.toString());
			str.append(')');
		}
		return str.toString();
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	public boolean hasSubConditions() {
		return !this.conditions.isEmpty();
	}
	
	public boolean isEmpty() {
		return this.getCondition()== null && !hasSubConditions(); 
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((condition == null) ? 0 : condition.hashCode());
		result = prime * result
				+ ((conditions == null) ? 0 : conditions.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QueryCondition other = (QueryCondition) obj;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		if (conditions == null) {
			if (other.conditions != null)
				return false;
		} else if (!conditions.equals(other.conditions))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	
}
