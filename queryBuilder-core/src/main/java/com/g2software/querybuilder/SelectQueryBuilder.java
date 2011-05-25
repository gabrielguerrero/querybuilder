package com.g2software.querybuilder;


public abstract class SelectQueryBuilder<E extends SelectQueryBuilder<?, ?>, T extends QueryExecutor>
		extends QueryBuilder<E, T> {

	private int firstResults = -1;

	private int maxResults = -1;
	private WhereClause<E> conditions = new WhereClause<E>(this);
	private FieldsCollection<E> fields = new FieldsCollection<E>(this);
	private StatementClause<E> groupByFields = new StatementClause<E>(this);
	private OrderByClause<E> orderByFields = new OrderByClause<E>(this);
	private StatementClause<E> joins = new StatementClause<E>(this);

	public SelectQueryBuilder(T queryExecutor) {
		super(queryExecutor);
	}

	@Override
	public E buildQuery() {

		StringBuilder queryWriter = new StringBuilder(select().size() * 20
				+ select().size() * 30 + where().size() * 30 + 40);
		queryWriter.append("select ");

		select().joinBy(',', queryWriter);

		queryWriter.append(" from ");

		from().joinBy(' ', queryWriter);

		if (!where().isEmpty()) {
			queryWriter.append(" where ");
			queryWriter.append(where().toString());
		}
		if (!groupBy().isEmpty()) {
			queryWriter.append(" group by ");
			groupBy().joinBy(',', queryWriter);
		}
		if (!orderBy().isEmpty()) {
			queryWriter.append(" order by ");
			orderBy().joinBy(',', queryWriter);
		}

		setBuiltQuery(queryWriter.toString());
		return (E) this;
	}

	public E clearFirstAndMaxResultLimits() {
		setMaxResults(-1);
		setFirstResults(-1);
		return (E) this;
	}

	public StatementClause<E> from() {
		return joins;
	}

	public StatementClause<E> from(String from) {
		return from().clear().add(from);
	}

	public int getFirstResults() {
		return firstResults;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public StatementClause<E> groupBy() {
		return groupByFields;
	}

	public E groupBy(String... groupBy) {
		groupBy().clear();
		for (int i = 0; i < groupBy.length; i++) {
			groupBy().add(groupBy[i]);
		}
		return groupBy().end();
	}

	public OrderByClause<E> orderBy() {
		return orderByFields;
	}

	public E orderBy(String... orderBy) {
		orderBy().clear();
		for (int i = 0; i < orderBy.length; i++) {
			orderBy().add(orderBy[i]);
		}
		return orderBy().end();
	}

	public FieldsCollection<E> select() {
		return fields;
	}

	public E select(String... fields) {
		select().clear();
		for (int i = 0; i < fields.length; i++) {
			select().add(fields[i]);
		}
		return select().end();
	}

	public E setFirstResults(int firstResult) {
		this.firstResults = firstResult;
		return (E) this;
	}

	public E setMaxResults(int maxResult) {
		this.maxResults = maxResult;
		return (E) this;
	}

	public E setParameter(String name, Object value) {
		getParameters().add(name, value);
		return (E) this;
	}

	public WhereClause<E> where() {
		return conditions;
	}

	public WhereClause<E> where(String where) {
		return where().clear().addAnd(where);
	}

	@Override
	public String toString() {
		return "query: " + getBuiltQuery() + " parameters: " + getParameters();
	}

	protected abstract E newQueryBuilder();

	public Object clone() {
		E queryBuilder = newQueryBuilder();
		queryBuilder.setFields(fields);
		queryBuilder.setJoins(joins);
		queryBuilder.setConditions(conditions);
		queryBuilder.setGroupByFields(groupByFields);
		queryBuilder.setOrderByFields(orderByFields);
		queryBuilder.setParameters(getParameters());
		queryBuilder.setFirstResults(firstResults);
		queryBuilder.setMaxResults(maxResults);
//		queryBuilder.setResultTransformer(resultTransformer);
		return queryBuilder;
	}

	@SuppressWarnings("unchecked")
	private void setConditions(WhereClause<?> conditions) {
		this.conditions = (WhereClause<E>) conditions;
	}

	@SuppressWarnings("unchecked")
	private void setFields(FieldsCollection<?> fields) {
		this.fields = (FieldsCollection<E>) fields;
	}

	@SuppressWarnings("unchecked")
	private void setGroupByFields(StatementClause<?> groupByFields) {
		this.groupByFields = (StatementClause<E>) groupByFields;
	}

	@SuppressWarnings("unchecked")
	protected final void setOrderByFields(OrderByClause<?> orderByFields) {
		this.orderByFields = (OrderByClause<E>) orderByFields;
	}

	@SuppressWarnings("unchecked")
	private void setJoins(StatementClause<?> joins) {
		this.joins = (StatementClause<E>) joins;
	}
}
