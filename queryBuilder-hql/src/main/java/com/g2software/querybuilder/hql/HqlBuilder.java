package com.g2software.querybuilder.hql;

import org.hibernate.Session;

import com.g2software.querybuilder.FieldsCollection;
import com.g2software.querybuilder.StatementClause;

public class HqlBuilder {

	private static HqlSessionProvider sessionProvider;

	public static HqlSelectQueryBuilder createSelect() {
		Session session = sessionProvider.getCurrentSession();
		HqlSelectQueryBuilder builder = new HqlSelectQueryBuilder(session);
		return builder;
	}

	public static FieldsCollection<HqlSelectQueryBuilder> select() {
		return createSelect().select();
	}

	public static HqlSelectQueryBuilder select(String fields) {
		return createSelect().select(fields);
	}

	public static void setSessionProvider(HqlSessionProvider sessionProvider) {
		HqlBuilder.sessionProvider = sessionProvider;
	}
}
