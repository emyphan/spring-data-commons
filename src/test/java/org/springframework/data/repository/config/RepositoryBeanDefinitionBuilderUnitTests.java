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
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.repository.config.RepositoryConfigurationExtensionSupportUnitTests.SampleRepositoryConfigurationExtension;

/**
 * Unit tests for {@link RepositoryBeanDefinitionBuilder}.
 * 
 * @author Oliver Gierke
 */
@RunWith(MockitoJUnitRunner.class)
public class RepositoryBeanDefinitionBuilderUnitTests {

	RepositoryBeanDefinitionBuilder builder;
	DefaultListableBeanFactory beanFactory;

	@Mock RepositoryConfiguration<RepositoryConfigurationSource> config;

	@Before
	public void setUp() {

		ResourceLoader resourceLoader = new DefaultResourceLoader(
				RepositoryBeanDefinitionBuilderUnitTests.class.getClassLoader());
		Environment environment = new StandardEnvironment();

		this.beanFactory = new DefaultListableBeanFactory();
		this.builder = new RepositoryBeanDefinitionBuilder(beanFactory, new SampleRepositoryConfigurationExtension(),
				resourceLoader, environment);
	}

	/**
	 * @see DATACMNS-622
	 */
	@Test
	public void setsUpCustomImplementationBeanForAbstractType() {

		when(config.getImplementationBeanName()).thenReturn("repositoryWithImplementationImpl");
		when(config.getImplementationClassName()).thenReturn("RepositoryWithImplementationImpl");
		when(config.getBasePackages()).thenReturn(Collections.singleton(getClass().getPackage().getName()));

		BeanDefinitionBuilder beanDefinitionBuilder = builder.build(config);
		assertThat(beanDefinitionBuilder, is(notNullValue()));

		AbstractBeanDefinition definition = beanDefinitionBuilder.getBeanDefinition();

		// Make sure the factory bean definition is considered primary to avoid autowiring ambiguities
		assertThat(definition.isPrimary(), is(true));

		BeanDefinition implementationDefinition = beanFactory.getBeanDefinition("repositoryWithImplementationImpl");

		// Make sure the implementation bean is not marked as abstract so that it gets created
		assertThat(implementationDefinition.isAbstract(), is(false));
		assertThat(implementationDefinition.isPrimary(), is(false));
	}
}
