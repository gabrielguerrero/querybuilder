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
package com.querybuilder;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class InsertQueryBuilderTest {

	InsertQueryBuilderExtension query;
	QueryExecutorExtension queryExecutor;
	
	@Before
	public void init(){
		queryExecutor = new QueryExecutorExtension();
		query = new InsertQueryBuilderExtension(queryExecutor);
	}
	
	@Test
	public void testQuery1(){
		query.insertInto("Customer");
		query.setFieldValue("ID", new Long(500));
		query.setFieldValue("FIRSTNAME", "Gabriel");
		query.setFieldValue("LASTNAME", "Guerrero");
		assertEquals("insert into Customer (ID,FIRSTNAME,LASTNAME) values (:ID,:FIRSTNAME,:LASTNAME)", query.build().getBuiltQuery());
	}
	@Test
	public void testQuery2(){
		query.insertInto("Customer")
			.setFieldValue("ID", new Long(500))
			.setFieldValue("FIRSTNAME", "Gabriel")
			.setFieldValue("LASTNAME", "Guerrero");
		
		assertEquals("insert into Customer (ID,FIRSTNAME,LASTNAME) values (:ID,:FIRSTNAME,:LASTNAME)", query.build().getBuiltQuery());
	}
	@Test
	public void testQuery3(){
		query.insertInto("CustomerCopy")
		.setFieldValue("ID", new Long(500))
		.setFieldValue("FIRSTNAME", "Gabriel")
		.setFieldValue("LASTNAME", "Guerrero");
		
		SelectQueryBuilderExtension selectQuery = new SelectQueryBuilderExtension(queryExecutor);
		selectQuery.select("*").from("Customer c");
		query.setValuesFrom(selectQuery);
		assertEquals("insert into CustomerCopy (ID,FIRSTNAME,LASTNAME) (select * from Customer c)", query.build().getBuiltQuery());
	}
}
