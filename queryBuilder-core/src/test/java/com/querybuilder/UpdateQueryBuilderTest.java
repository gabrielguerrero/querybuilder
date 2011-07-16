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

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class UpdateQueryBuilderTest {

	UpdateQueryBuilderExtension query;
	QueryExecutorExtension queryExecutor;
	
	@Before
	public void init(){
		queryExecutor = new QueryExecutorExtension();
		query = new UpdateQueryBuilderExtension(queryExecutor);
	}
	
	@Test
	public void testQuery1(){
		query.update("Customer");
		query.setFieldValue("ID", new Long(500));
		query.setFieldValue("FIRSTNAME", "Gabriel");
		query.setFieldValue("LASTNAME", "Guerrero");
		assertEquals("update Customer set ID = :ID,FIRSTNAME = :FIRSTNAME,LASTNAME = :LASTNAME", query.build().getBuiltQuery());
	}
	@Test
	public void testQuery2(){
		query.update("Customer")
			.setFieldValue("ID", new Long(500))
			.setFieldValue("FIRSTNAME", "Gabriel")
			.setFieldValue("LASTNAME", "Guerrero");
		
		assertEquals("update Customer set ID = :ID,FIRSTNAME = :FIRSTNAME,LASTNAME = :LASTNAME", query.build().getBuiltQuery());
	}
	@Test
	public void testQuery3(){
		query.update("Customer c")
		.setFieldValue("ID", new Long(500))
		.setFieldValue("FIRSTNAME", "Gabriel")
		.setFieldValue("LASTNAME", "Guerrero").where("c.id >=:oldids").setParameter("oldids", 30);
		
		assertEquals("update Customer c set ID = :ID,FIRSTNAME = :FIRSTNAME,LASTNAME = :LASTNAME where (c.id >=:oldids)", query.build().getBuiltQuery());
	}
	@Test
	public void testQuery4(){
		query.update("Customer c")
		.setFieldValue("ID", new Long(500)).where("c.id in :ids");
		
		SelectQueryBuilderExtension selectQuery = new SelectQueryBuilderExtension(queryExecutor);
		selectQuery.select("cd.id").from("Customer cb").where("cd.id<:newids").setParameter("newids", 10);
		query.setParameter("ids", selectQuery);
		assertEquals("update Customer c set ID = :ID where (c.id in (select cd.id from Customer cb where (cd.id<:newids)))", query.build().getBuiltQuery());
	}
	
	@Test
	public void testQuery5(){
		SelectQueryBuilderExtension selectQuery = new SelectQueryBuilderExtension(queryExecutor);
		selectQuery.select("customers.name").from("customers").where("customers.customer_id = suppliers.supplier_id");
		
		query.update("Suppliers")
		.setFieldValue("supplier_name",selectQuery).where("exists :supplier_name");
		// Whenever a supplier_id matched a customer_id value, the supplier_name would be overwritten to the customer name from the customers table
		assertEquals("update Suppliers set supplier_name = (select customers.name from customers where (customers.customer_id = suppliers.supplier_id))  where (exists (select customers.name from customers where (customers.customer_id = suppliers.supplier_id)))", query.build().getBuiltQuery());
	}
}
