package com.g2software.querybuilder;

public class OrderByClause<T extends QueryBuilder> extends StatementClause<T>{

	OrderByClause(QueryBuilder parentStatement) {
		super(parentStatement);
	}
	
	public OrderByClause<T> add(String field,boolean ascending){
		add(field+ (ascending ? " asc" :" desc"));
		return this;
	}
	public OrderByClause<T> remove(String field,boolean ascending){
		remove(field+ (ascending ? " asc" :" desc"));
		return this;
	}
	public OrderByClause<T> addAsc(String field){
		add(field,true);
		return this;
	}
	public OrderByClause<T> removeAsc(String field){
		remove(field,true);
		return this;
	}
	public OrderByClause<T> addDesc(String field){
		add(field,false);
		return this;
	}
	public OrderByClause<T> removeDesc(String field){
		remove(field,false);
		return this;
	}

}
