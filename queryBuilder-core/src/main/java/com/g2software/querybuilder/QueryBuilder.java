package com.g2software.querybuilder;


public abstract class QueryBuilder<E extends QueryBuilder<?,?>, T extends QueryExecutor> {

	private String query;
	private ParameterCollection parameters = new ParameterCollection();
	private T queryExecutor;
	public QueryBuilder(T queryExecutor) {
		this.queryExecutor = queryExecutor;
	}

	public T execute() {
		if (getBuiltQuery()==null){
			build();
		}
		queryExecutor.init(this);
		return queryExecutor;
	}

	public E build()  {
		return (E) this;
	}

	public String getBuiltQuery() {
		return query;
	}

	public ParameterCollection getParameters() {
		return parameters;
	}

	protected final T getQueryExecutor() {
		return queryExecutor;
	}

	protected void setBuiltQuery(String query) {
		this.query = query;
	}
	
	protected final void setParameters(ParameterCollection parameters) {
		this.parameters = parameters;
	}

	protected void queryChanged(){
		if (getBuiltQuery()!=null)
			setBuiltQuery(null);
	}
}
