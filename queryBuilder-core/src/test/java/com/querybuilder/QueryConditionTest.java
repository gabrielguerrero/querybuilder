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

import org.junit.Test;

import com.querybuilder.QueryCondition;

public class QueryConditionTest {

	@Test
	public void testToString() {
		{
			QueryCondition con = QueryCondition.newAndCondition("0").addAnd("1").addOr("2").addCondition(QueryCondition.newAndCondition("3").addAnd("4"));
			assertEquals("(0) and (1) or (2) and ((3) and (4))", con.toString());
		}
		{
			QueryCondition con = QueryCondition.newAndCondition("0");
			assertEquals("0", con.toString());
		}
		{
			QueryCondition con = QueryCondition.newAndCondition().addAnd("1").addOr("2").addCondition(QueryCondition.newAndCondition("3").addAnd("4"));
			assertEquals("(1) or (2) and ((3) and (4))", con.toString());
		}
	}

}
