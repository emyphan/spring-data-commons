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
package org.springframework.data.repository.config;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

/**
 * Unit tests for {@link CustomRepositoryImplementationDetector}.
 * 
 * @author Oliver Gierke
 */
public class CustomRepositoryImplementationDetectorUnitTests {

	CustomRepositoryImplementationDetector detector;

	@Before
	public void setUp() {

		ResourceLoader resourceLoader = new DefaultResourceLoader(
				CustomRepositoryImplementationDetectorUnitTests.class.getClassLoader());
		MetadataReaderFactory metadataReaderFactory = new SimpleMetadataReaderFactory(resourceLoader);

		this.detector = new CustomRepositoryImplementationDetector(metadataReaderFactory, new StandardEnvironment(),
				resourceLoader);
	}

	/**
	 * @see DATACMNS-622
	 */
	@Test
	public void detectsNestedAbstractImplementation() {

		AbstractBeanDefinition definition = detector.detectCustomImplementation(RepositoryWithImplementation.class
				.getSimpleName().concat("Impl"), Collections.singleton(RepositoryWithImplementation.class.getPackage()
				.getName()));

		assertThat(definition, is(notNullValue()));
		assertThat(definition.isAbstract(), is(true));
	}
}
