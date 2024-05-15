package com.example.battleship.configuration;

import javax.sql.DataSource;
import com.example.battleship.security.entity.RefreshToken;
import com.example.battleship.security.repository.RefreshTokenRepository;
import com.example.battleship.user.entity.User;
import com.example.battleship.user.repository.UserRepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackageClasses = {UserRepository.class, RefreshTokenRepository.class},
        entityManagerFactoryRef = "mysqlEntityManagerFactory",
        transactionManagerRef = "mysqlTransactionManager"
)
public class MySqlJpaConfiguration {

  @Primary
  @Bean
  public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
          @Qualifier("mysqlDataSource") DataSource dataSource,
          EntityManagerFactoryBuilder builder
  ) {

    return builder
            .dataSource(dataSource)
            .packages(User.class, RefreshToken.class)
            .build();
  }

  @Primary
  @Bean
  public JpaTransactionManager  mysqlTransactionManager(
          @Qualifier("mysqlEntityManagerFactory") EntityManagerFactory entityManagerFactory
  ) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);

    return transactionManager;
  }
}
