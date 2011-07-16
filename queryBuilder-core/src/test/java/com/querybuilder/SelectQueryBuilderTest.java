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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;


import org.junit.Before;
import org.junit.Test;


@SuppressWarnings({"rawtypes","unchecked"})
public class SelectQueryBuilderTest {
	
	SelectQueryBuilderExtension query;
	QueryExecutorExtension queryExecutor ;

	@Before
	public void init(){
		queryExecutor = new QueryExecutorExtension();
		query = new SelectQueryBuilderExtension(queryExecutor);
	}
	
	
	@Test
	public void testQuery1(){
		query.select("*").from("Customer");
		assertEquals("select * from Customer", query.build().getBuiltQuery());
	}
	@Test
	public void testQuery2(){
		query.select("*").from("Customer c","inner join  Invoice i on i.customerId=c.id");
		assertEquals("select * from Customer c inner join  Invoice i on i.customerId=c.id", query.build().getBuiltQuery());
	}
	@Test
	public void testQuery3(){
		query.select("*").from("Customer c","inner join  Invoice i on i.customerId=c.id").where("c.firstName like :name").setParameter("name", "Andrew");
		assertEquals("select * from Customer c inner join  Invoice i on i.customerId=c.id where (c.firstName like :name)", query.build().getBuiltQuery());
	}
	@Test
	public void testQuery4(){
		query.select("*");
		query.from("Customer c");
		query.from().add("inner join  Invoice i on i.customerId=c.id");
		query.where("c.firstName like :name");
		query.orderBy("c.firstName",false);
		query.setParameter("name", "Andrew");
		
		assertEquals("select * from Customer c inner join  Invoice i on i.customerId=c.id where (c.firstName like :name) order by c.firstName desc", query.build().getBuiltQuery());
	}	
	@Test
	public void testQuery5(){
			query.select("*");
			query.from("Customer c");
			query.from().add("inner join  Invoice i on i.customerId=c.id");
			query.where("c.firstName like :name");
			query.groupBy("c.firstName");
			query.orderBy("c.firstName",false);
			query.setParameter("name", "Andrew");
			
			assertEquals("select * from Customer c inner join  Invoice i on i.customerId=c.id where (c.firstName like :name) group by c.firstName order by c.firstName desc", query.build().getBuiltQuery());
	}
	@Test
	public void testQuery6(){
		query.from("Customer c","inner join  Invoice i on i.customerId=c.id").where("c.firstName like :name").setParameter("name", "Andrew");
		query.where().addAnd("c.id in :customers");
		SelectQueryBuilderExtension subquery = new SelectQueryBuilderExtension(queryExecutor);
		subquery.select("b.id").from("Customer b").where("b.id<10","b.lastName like :lastName").setParameter("lastName", "P");
		query.setParameter("customers", subquery);
		assertEquals("select * from Customer c inner join  Invoice i on i.customerId=c.id where (c.firstName like :name) and (c.id in (select b.id from Customer b where (b.id<10) and (b.lastName like :lastName)))", query.build().getBuiltQuery());
		assertNotNull(query.getParameters().get("lastName"));
	}
	
}
