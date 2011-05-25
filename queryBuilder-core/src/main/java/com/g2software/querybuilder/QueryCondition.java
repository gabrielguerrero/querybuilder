package com.g2software.querybuilder;
import java.util.ArrayList;
import java.util.List;

public class QueryCondition {
	private List conditions = new ArrayList();
	public static final int AND = 0;
	public static final int OR = 1;
	private int type = AND;
	private String condition;
	
	public static QueryCondition createAndCondition() {
		return new QueryCondition(AND);
	}
	public static QueryCondition createAndCondition(String condition) {
		return new QueryCondition(condition,AND);
	}
	public static QueryCondition createOrCondition(String condition) {
		return new QueryCondition(condition,OR);
	}
	public static QueryCondition createOrCondition() {
		return new QueryCondition(OR);
	}
	public QueryCondition(int type) {
		this.type = type;
	}
	private QueryCondition(String condition,int type) {
		this.type = type;
		this.condition = condition;
	}
	private QueryCondition() {
		this.type = AND;
	}
	
	public QueryCondition addAndSubCondition(String condition) {
		if (condition!=null)
		conditions.add(new QueryCondition(condition,QueryCondition.AND));
		return this;
	}
	public QueryCondition addOrSubCondition(String condition) {
		if (condition!=null)
		conditions.add(new QueryCondition(condition,QueryCondition.OR));
		return this;
	}
	public QueryCondition addSubCondition(QueryCondition condition) {
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
		
		if (condition!=null)
			str.append(condition);
		
		for (int i = 0; i < conditions.size(); i++) {
			QueryCondition condition = (QueryCondition) conditions.get(i);
			if (i>0) {
				if (condition.type == QueryCondition.AND)
					str.append(" and ");
				else
					str.append(" or ");
			}	
			str.append('(');
			str.append(condition.toString());
			str.append(')');
		}
		return str.toString();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
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
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((condition == null) ? 0 : condition.hashCode());
		result = PRIME * result + ((conditions == null) ? 0 : conditions.hashCode());
		result = PRIME * result + type;
		return result;
	}
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final QueryCondition other = (QueryCondition) obj;
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
