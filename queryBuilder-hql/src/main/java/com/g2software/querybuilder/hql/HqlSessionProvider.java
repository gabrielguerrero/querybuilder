package com.g2software.querybuilder.hql;

import org.hibernate.Session;

public interface HqlSessionProvider {
	public Session getCurrentSession();
}
