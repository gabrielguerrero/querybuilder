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
package com.querybuilder.transformer;

import java.util.HashMap;
import java.util.Map;

public class MapResultTransformer implements ReturnResult, AddResultFromArray {

	Map mapResult;

	public MapResultTransformer(int initialMapCapacity) {
		this.mapResult = new HashMap(initialMapCapacity);
	}

	public MapResultTransformer(int mapInitialCapacity, float mapLoadFactor) {
		this.mapResult = new HashMap(mapInitialCapacity, mapLoadFactor);
	}

	public MapResultTransformer(Map mapResult) {
		this.mapResult = mapResult;
	}

	public Object addResult(Object[] values, String[] aliases) {
		if (values.length == 1) {
			mapResult.put(values[0], Boolean.TRUE);
			return null;
		} else if (values.length == 2) {
			mapResult.put(values[0], values[1]);
			return null;
		}
		Map row = new HashMap(values.length + ((int) (values.length * 0.25)));
		for (int i = 1; i < aliases.length; i++) {
			String alias = aliases[i];
			if (alias == null)
				alias = "field" + i;
			row.put(alias, values[i]);
		}
		mapResult.put(values[0], row);
		return null;
	}

	public Object returnResult() {
		return mapResult;
	}

}
