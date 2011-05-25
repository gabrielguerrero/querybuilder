package com.g2software.querybuilder.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ValueObjectFromResultSet<V> {

	public abstract V getValueObject(ResultSet rs) throws SQLException;

}