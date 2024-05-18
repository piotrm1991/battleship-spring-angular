package com.example.battleship.configuration;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MultipleDatabaseConfiguration {

  @Bean("mysql")
  @Primary
  @ConfigurationProperties("spring.mysql.datasource")
  public DataSourceProperties mysqlDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean("h2")
  @ConfigurationProperties("spring.h2.datasource")
  public DataSourceProperties h2DataSourceProperties() {
    return new DataSourceProperties();
  }


  @Bean(name = "mysqlDataSource")
  @ConfigurationProperties("spring.mysql.datasource")
  @Primary
  public HikariDataSource dataSourceMysql(@Qualifier("mysql") DataSourceProperties dataSourceProperties){

    return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
  }

  @Bean(name = "h2DataSource")
  @ConfigurationProperties("spring.h2.datasource")
  public DataSource dataSourceH2(@Qualifier("h2") DataSourceProperties dataSourceProperties){

    return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
  }
}
