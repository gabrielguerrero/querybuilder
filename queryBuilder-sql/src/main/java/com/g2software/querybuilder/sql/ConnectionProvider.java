package com.g2software.querybuilder.sql;

import java.sql.Connection;

public interface ConnectionProvider {

	Connection get();
}
