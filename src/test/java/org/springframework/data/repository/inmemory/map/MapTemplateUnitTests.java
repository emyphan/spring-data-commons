/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.repository.inmemory.map;

import org.springframework.data.repository.inmemory.GenericInMemoryOperationsUnitTests;
import org.springframework.data.repository.inmemory.InMemoryOperations;
import org.springframework.data.repository.inmemory.InMemoryQuery;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author Christoph Strobl
 */
public class MapTemplateUnitTests extends GenericInMemoryOperationsUnitTests {

	@Override
	protected InMemoryOperations getInMemoryOperations() {
		return new MapTemplate();
	}

	@Override
	protected InMemoryQuery getInMemoryQuery() {
		return new MapQuery(new SpelExpressionParser().parseExpression("foo == 'two'"));
	}

}