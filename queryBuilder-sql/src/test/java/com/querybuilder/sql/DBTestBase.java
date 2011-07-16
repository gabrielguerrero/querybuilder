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
package com.querybuilder.sql;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hsqldb.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class DBTestBase {

	static Server hsqlServer;
	@BeforeClass
	public static void start() throws Exception{
		
		hsqlServer = new Server();
		// HSQLDB prints out a lot of informations when
        // starting and closing, which we don't need now.
        // Normally you should point the setLogWriter
        // to some Writer object that could store the logs.
        hsqlServer.setLogWriter(null);
        hsqlServer.setSilent(true);

        // The actual database will be named 'xdb' and its
        // settings and data will be stored in files
        // testdb.properties and testdb.script
        hsqlServer.setDatabaseName(0, "xdb");
        hsqlServer.setDatabasePath(0, "file:testdb");

        // Start the database!
        hsqlServer.start();
        
        Connection connection = getConnection();
        BufferedReader input =  new BufferedReader(new InputStreamReader(DBTestBase.class.getResourceAsStream("/sampledata.sql")));
        try {
            String line = null; //not declared within while loop
            /*
            * readLine is a bit quirky :
            * it returns the content of a line MINUS the newline.
            * it returns null only for the END of the stream.
            * it returns an empty String if two newlines appear in a row.
            */
            while (( line = input.readLine()) != null){
            	connection.prepareStatement(line).execute();
            }
            connection.commit();
          }
          finally {
            input.close();
          }

	}
	
	@AfterClass
	public static  void stop(){
		hsqlServer.stop();
	}
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException{
		Connection connection = null;
		// We have here two 'try' blocks and two 'finally'
		// blocks because we have two things to close
		// after all - HSQLDB server and connection
		// Getting a connection to the newly started database
		Class.forName("org.hsqldb.jdbcDriver");
		// Default user of the HSQLDB is 'sa'
		// with an empty password
		connection = DriverManager.getConnection(
				"jdbc:hsqldb:hsql://localhost/xdb", "sa", "");
		return connection;
            
	}
	
	
}
