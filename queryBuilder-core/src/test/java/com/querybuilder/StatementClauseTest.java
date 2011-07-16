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
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.querybuilder.QueryBuilder;
import com.querybuilder.StatementClause;

public class StatementClauseTest {

	QueryBuilder queryBuilder;
	private StatementClause statementClause;
	

	@Before
	public void init(){
		queryBuilder = mock(QueryBuilder.class);
		statementClause = new StatementClause<QueryBuilder>(queryBuilder);
	}
	@Test
	public void testAddString() {
		statementClause.add("0");
		statementClause.add("1");
		statementClause.add("2");
		assertTrue(statementClause.getStatements().contains("0"));
		assertTrue(statementClause.getStatements().contains("1"));
		assertTrue(statementClause.getStatements().contains("2"));
	}
	@Test
	public void testAddIntString() {
		statementClause.add("0");
		statementClause.add(1,"1");
		statementClause.add("2");
		assertTrue(statementClause.getStatements().contains("0"));
		assertTrue(statementClause.getStatements().contains("1"));
		assertTrue(statementClause.getStatements().contains("2"));
		
		statementClause.add(1,"x");
		assertTrue(statementClause.getStatements().indexOf("x")==1);
		
	}


	@Test
	public void testAddAfter() {
		statementClause.add("0");
		statementClause.add("1");
		statementClause.add("2");
		assertTrue(statementClause.getStatements().contains("0"));
		assertTrue(statementClause.getStatements().contains("1"));
		assertTrue(statementClause.getStatements().contains("2"));
		
		statementClause.addAfter("x","0");
		assertTrue(statementClause.getStatements().indexOf("x")==1);
	}

	@Test
	public void testAddAll() {
		statementClause.add("0");
		statementClause.add("1");
		statementClause.add("2");
		StatementClause newStament = new StatementClause(queryBuilder);
		newStament.add("3");
		newStament.add("4");
		newStament.add("5");
		statementClause.addAll(newStament);
		assertTrue(statementClause.getStatements().contains("0"));
		assertTrue(statementClause.getStatements().contains("1"));
		assertTrue(statementClause.getStatements().contains("2"));
		assertTrue(statementClause.getStatements().contains("3"));
		assertTrue(statementClause.getStatements().contains("4"));
		assertTrue(statementClause.getStatements().contains("5"));
		
	}

	@Test
	public void testAddBefore() {
		statementClause.add("0");
		statementClause.add("1");
		statementClause.add("2");
		assertTrue(statementClause.getStatements().contains("0"));
		assertTrue(statementClause.getStatements().contains("1"));
		assertTrue(statementClause.getStatements().contains("2"));
		
		statementClause.addBefore("x","1");
		assertTrue(statementClause.getStatements().indexOf("x")==1);
	}

	@Test
	public void testAddLast() {
		statementClause.add("0");
		statementClause.add("1");
		statementClause.add("2");
		assertTrue(statementClause.getStatements().contains("0"));
		assertTrue(statementClause.getStatements().contains("1"));
		assertTrue(statementClause.getStatements().contains("2"));
		
		statementClause.addLast("x");
		assertTrue(statementClause.getStatements().indexOf("x")==3);
	}

	@Test
	public void testClear() {
		statementClause.add("0");
		statementClause.add("1");
		statementClause.add("2");
		statementClause.clear();
		assertFalse(statementClause.getStatements().contains("0"));
		assertFalse(statementClause.getStatements().contains("1"));
		assertFalse(statementClause.getStatements().contains("2"));
	}

	@Test
	public void testIsEmpty() {
		assertTrue(statementClause.getStatements().isEmpty());
		statementClause.add("0");
		statementClause.add("1");
		statementClause.add("2");
		assertFalse(statementClause.getStatements().isEmpty());
		statementClause.clear();
		assertTrue(statementClause.getStatements().isEmpty());
	}

	@Test
	public void testEnd() {
		assertEquals(queryBuilder, statementClause.end());
	}

	@Test
	public void testGetStatements() {
		statementClause.add("0");
		statementClause.add("1");
		statementClause.add("2");
		assertNotNull(statementClause.getStatements());
		assertTrue(statementClause.getStatements().contains("0"));
		assertTrue(statementClause.getStatements().contains("1"));
		assertTrue(statementClause.getStatements().contains("2"));
	}

	@Test
	public void testJoinBy() {
		statementClause.add("0");
		statementClause.add("1");
		statementClause.add("2");
		StringBuilder builder = new StringBuilder();
		statementClause.joinBy(',', builder);
		assertEquals("0,1,2", builder.toString());
	}

	@Test
	public void testRemoveInt() {
		statementClause.add("0");
		statementClause.add("1");
		statementClause.add("2");
		statementClause.remove(1);
		assertTrue(statementClause.getStatements().contains("0"));
		assertFalse(statementClause.getStatements().contains("1"));
		assertTrue(statementClause.getStatements().contains("2"));
	}

	@Test
	public void testRemoveString() {
		statementClause.add("0");
		statementClause.add("1");
		statementClause.add("2");
		statementClause.remove("1");
		assertTrue(statementClause.getStatements().contains("0"));
		assertFalse(statementClause.getStatements().contains("1"));
		assertTrue(statementClause.getStatements().contains("2"));
	}

	@Test
	public void testReplace() {
		statementClause.add("0");
		statementClause.add("1");
		statementClause.add("2");
		statementClause.replace("x","1");
		assertTrue(statementClause.getStatements().contains("0"));
		assertFalse(statementClause.getStatements().contains("1"));
		assertTrue(statementClause.getStatements().contains("2"));
		assertTrue(statementClause.getStatements().contains("x"));
		assertTrue(statementClause.getStatements().indexOf("x")==1);
	}

	@Test
	public void testSetStatements() {
		statementClause.add("0");
		statementClause.add("1");
		statementClause.add("2");
		StatementClause newStament = new StatementClause(queryBuilder);
		newStament.add("3");
		newStament.add("4");
		newStament.add("5");
		statementClause.setStatements(newStament.getStatements());
		assertFalse(statementClause.getStatements().contains("0"));
		assertFalse(statementClause.getStatements().contains("1"));
		assertFalse(statementClause.getStatements().contains("2"));
		assertTrue(statementClause.getStatements().contains("3"));
		assertTrue(statementClause.getStatements().contains("4"));
		assertTrue(statementClause.getStatements().contains("5"));
	}

	@Test
	public void testSize() {
		statementClause.add("0");
		statementClause.add("1");
		statementClause.add("2");
		assertTrue(statementClause.size()==3);
	}

}
