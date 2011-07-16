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

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import com.querybuilder.QueryBuilder;
import com.querybuilder.QueryCondition;
import com.querybuilder.WhereClause;

public class WhereClauseTest {

	QueryBuilder queryBuilder;
	private WhereClause whereClause;
	

	@Before
	public void init(){
		queryBuilder = mock(QueryBuilder.class);
		whereClause = new WhereClause<QueryBuilder>(queryBuilder);
	}
	

	@Test
	public void testAddAnd() {
		whereClause.addAnd("0");
		whereClause.addAnd("1");
		whereClause.addAnd("2");
		LinkedList<QueryCondition> conditions = whereClause.getConditions();
		assertTrue(conditions.contains(QueryCondition.newAndCondition("0")));
		assertTrue(conditions.contains(QueryCondition.newAndCondition("1")));
		assertTrue(conditions.contains(QueryCondition.newAndCondition("2")));
	}

	@Test
	public void testAddCondition() {
		whereClause.addCondition(QueryCondition.newAndCondition("0"));
		whereClause.addCondition(QueryCondition.newAndCondition("1"));
		whereClause.addCondition(QueryCondition.newAndCondition("2"));
		LinkedList<QueryCondition> conditions = whereClause.getConditions();
		assertTrue(conditions.contains(QueryCondition.newAndCondition("0")));
		assertTrue(conditions.contains(QueryCondition.newAndCondition("1")));
		assertTrue(conditions.contains(QueryCondition.newAndCondition("2")));
	}

	@Test
	public void testAddOr() {
		whereClause.addOr("0");
		whereClause.addOr("1");
		whereClause.addOr("2");
		LinkedList<QueryCondition> conditions = whereClause.getConditions();
		assertTrue(conditions.contains(QueryCondition.newOrCondition("0")));
		assertTrue(conditions.contains(QueryCondition.newOrCondition("1")));
		assertTrue(conditions.contains(QueryCondition.newOrCondition("2")));
	}
	@Test
	public void testAddAll() {
		whereClause.addOr("0");
		whereClause.addOr("1");
		whereClause.addOr("2");
		WhereClause whereClause2 = new WhereClause<QueryBuilder>(queryBuilder);
		whereClause2.addOr("3");
		whereClause2.addOr("4");
		whereClause2.addOr("5");
		whereClause.addAll(whereClause2);
		LinkedList<QueryCondition> conditions = whereClause.getConditions();
		assertTrue(conditions.contains(QueryCondition.newOrCondition("0")));
		assertTrue(conditions.contains(QueryCondition.newOrCondition("1")));
		assertTrue(conditions.contains(QueryCondition.newOrCondition("2")));
		assertTrue(conditions.contains(QueryCondition.newOrCondition("3")));
		assertTrue(conditions.contains(QueryCondition.newOrCondition("4")));
		assertTrue(conditions.contains(QueryCondition.newOrCondition("5")));
	}

	@Test
	public void testEnd() {
		assertEquals(queryBuilder, whereClause.end());
	}

	@Test
	public void testGetConditions() {
		whereClause.addOr("0");
		whereClause.addOr("1");
		whereClause.addOr("2");
		LinkedList<QueryCondition> conditions = whereClause.getConditions();
		assertNotNull(conditions);
		assertTrue(conditions.contains(QueryCondition.newOrCondition("0")));
		assertTrue(conditions.contains(QueryCondition.newOrCondition("1")));
		assertTrue(conditions.contains(QueryCondition.newOrCondition("2")));
	}

	@Test
	public void testIsEmpty() {
		assertTrue(whereClause.getConditions().isEmpty());
		whereClause.addOr("0");
		whereClause.addOr("1");
		whereClause.addOr("2");
		LinkedList<QueryCondition> conditions = whereClause.getConditions();
		assertNotNull(conditions);
		assertFalse(whereClause.getConditions().isEmpty());
		whereClause.clear();
		assertTrue(whereClause.getConditions().isEmpty());
	}

	@Test
	public void testClear() {
		assertTrue(whereClause.getConditions().isEmpty());
		whereClause.addOr("0");
		whereClause.addOr("1");
		whereClause.addOr("2");
		LinkedList<QueryCondition> conditions = whereClause.getConditions();
		assertNotNull(conditions);
		assertFalse(whereClause.getConditions().isEmpty());
		whereClause.clear();
		assertTrue(whereClause.getConditions().isEmpty());
	}

	@Test
	public void testRemoveAndCondition() {
		whereClause.addAnd("0");
		whereClause.addAnd("1");
		whereClause.addAnd("2");
		whereClause.removeAndCondition("1");
		LinkedList<QueryCondition> conditions = whereClause.getConditions();
		assertNotNull(conditions);
		assertTrue(conditions.contains(QueryCondition.newAndCondition("0")));
		assertFalse(conditions.contains(QueryCondition.newAndCondition("1")));
		assertTrue(conditions.contains(QueryCondition.newAndCondition("2")));
	}

	@Test
	public void testRemoveCondition() {
		whereClause.addCondition(QueryCondition.newAndCondition("0"));
		whereClause.addCondition(QueryCondition.newAndCondition("1"));
		whereClause.addCondition(QueryCondition.newAndCondition("2"));
		whereClause.removeCondition(QueryCondition.newAndCondition("1"));
		LinkedList<QueryCondition> conditions = whereClause.getConditions();
		assertTrue(conditions.contains(QueryCondition.newAndCondition("0")));
		assertFalse(conditions.contains(QueryCondition.newAndCondition("1")));
		assertTrue(conditions.contains(QueryCondition.newAndCondition("2")));
	}

	@Test
	public void testRemoveOrCondition() {
		whereClause.addOr("0");
		whereClause.addOr("1");
		whereClause.addOr("2");
		whereClause.removeOrCondition("1");
		LinkedList<QueryCondition> conditions = whereClause.getConditions();
		assertNotNull(conditions);
		assertTrue(conditions.contains(QueryCondition.newOrCondition("0")));
		assertFalse(conditions.contains(QueryCondition.newOrCondition("1")));
		assertTrue(conditions.contains(QueryCondition.newOrCondition("2")));
	}

	@Test
	public void testSetConditions() {
		whereClause.addOr("0");
		whereClause.addOr("1");
		whereClause.addOr("2");
		WhereClause whereClause2 = new WhereClause<QueryBuilder>(queryBuilder);
		whereClause2.addOr("3");
		whereClause2.addOr("4");
		whereClause2.addOr("5");
		whereClause.setConditions(whereClause2.getConditions());
		LinkedList<QueryCondition> conditions = whereClause.getConditions();
		assertFalse(conditions.contains(QueryCondition.newOrCondition("0")));
		assertFalse(conditions.contains(QueryCondition.newOrCondition("1")));
		assertFalse(conditions.contains(QueryCondition.newOrCondition("2")));
		conditions = whereClause.getConditions();
		assertTrue(conditions.contains(QueryCondition.newOrCondition("3")));
		assertTrue(conditions.contains(QueryCondition.newOrCondition("4")));
		assertTrue(conditions.contains(QueryCondition.newOrCondition("5")));
	}

	@Test
	public void testSize() {
		whereClause.addOr("0");
		whereClause.addOr("1");
		whereClause.addOr("2");
		assertEquals(3,whereClause.size());
	}

	@Test
	public void testToString() {
		whereClause.addOr("0");
		whereClause.addAnd("1");
		whereClause.addOr("2");
		whereClause.addAnd("3");
		assertEquals("(0) and (1) or (2) and (3)",whereClause.toString());
		
	}

}
