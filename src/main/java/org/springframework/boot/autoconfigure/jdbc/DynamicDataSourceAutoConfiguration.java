/**
 * Copyright © 2018 TaoYu (tracy5546@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.boot.autoconfigure.jdbc;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DynamicDataSourceAspect;
import org.springframework.jdbc.datasource.DynamicDataSourceProvider;
import org.springframework.jdbc.datasource.DynamicDataSourceStrategy;
import org.springframework.jdbc.datasource.DynamicRoutingDataSource;
import org.springframework.jdbc.datasource.LoadBalanceDynamicDataSourceStrategy;
import org.springframework.jdbc.datasource.YmlDynamicDataSourceProvider;

/**
 * DynamicDataSourceAutoConfiguration
 *
 * @author TaoYu Kanyuxia
 * @since 1.0.0
 * @see DynamicDataSourceProvider
 * @see DynamicDataSourceStrategy
 * @see DynamicRoutingDataSource
 * @see DynamicDataSourceAspect
 */
@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class DynamicDataSourceAutoConfiguration {

  private final DynamicDataSourceProperties properties;

  public DynamicDataSourceAutoConfiguration(DynamicDataSourceProperties properties) {
    this.properties = properties;
  }

  @Bean
  @ConditionalOnMissingBean
  public DynamicDataSourceStrategy dynamicDataSourceStrategy() {
    return new LoadBalanceDynamicDataSourceStrategy();
  }

  @Bean
  @ConditionalOnMissingBean
  public DynamicDataSourceProvider dynamicDataSourceProvider() {
    return new YmlDynamicDataSourceProvider(properties);
  }

  @Bean
  @ConditionalOnMissingBean
  public DynamicRoutingDataSource dynamicDataSource(DynamicDataSourceProvider dynamicDataSourceProvider,
      DynamicDataSourceStrategy dynamicDataSourceStrategy) {
    DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
    dynamicRoutingDataSource.setDynamicDataSourceProvider(dynamicDataSourceProvider);
    dynamicRoutingDataSource.setDynamicDataSourceStrategy(dynamicDataSourceStrategy);
    return dynamicRoutingDataSource;
  }

  @Bean
  @ConditionalOnMissingBean
  public DynamicDataSourceAspect dynamicDataSourceAspect() {
    return new DynamicDataSourceAspect();
  }

}