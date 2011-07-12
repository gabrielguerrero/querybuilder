package com.g2software.querybuilder;
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
