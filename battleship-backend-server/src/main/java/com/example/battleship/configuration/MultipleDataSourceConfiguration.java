package com.example.battleship.configuration;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Configuration for multiple datasources.
 */
@Configuration
public class MultipleDataSourceConfiguration {

  @Bean("mysql")
  @Primary
  @ConfigurationProperties("spring.mysql.datasource")
  DataSourceProperties mysqlDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean("h2")
  @ConfigurationProperties("spring.h2.datasource")
  DataSourceProperties h2DataSourceProperties() {
    return new DataSourceProperties();
  }


  @Bean(name = "mysqlDataSource")
  @ConfigurationProperties("spring.mysql.datasource")
  @Primary
  HikariDataSource dataSourceMysql(@Qualifier("mysql") DataSourceProperties dataSourceProperties) {

    return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
  }

  @Bean(name = "h2DataSource")
  @ConfigurationProperties("spring.h2.datasource")
  DataSource dataSourceH2(@Qualifier("h2") DataSourceProperties dataSourceProperties) {

    return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
  }
}
