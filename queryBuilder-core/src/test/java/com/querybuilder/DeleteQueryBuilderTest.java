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

public class DeleteQueryBuilderTest {

	DeleteQueryBuilderExtension query;
	QueryExecutorExtension queryExecutor;
	
	@Before
	public void init(){
		queryExecutor = new QueryExecutorExtension();
		query = new DeleteQueryBuilderExtension(queryExecutor);
	}
	
	@Test
	public void testQuery1(){
		query.deleteFrom("Customer c");
		query.where("c.id=:id");
		query.setParameter("id", new Long(500));
		assertEquals("delete from Customer c where (c.id=:id)", query.build().getBuiltQuery());
	}
	@Test
	public void testQuery2(){
		
		query.deleteFrom("Customer c");
		query.where("c.id in :ids");
		query.setParameter("id", new Long(500));
		
		SelectQueryBuilderExtension selectQuery = new SelectQueryBuilderExtension(queryExecutor);
		selectQuery.select("cd.id").from("Customer cb").where("cd.id<:newids").setParameter("newids", 10);
		query.setParameter("ids", selectQuery);
		
		assertEquals("delete from Customer c where (c.id in (select cd.id from Customer cb where (cd.id<:newids)))", query.build().getBuiltQuery());
	}
}
