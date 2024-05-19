package com.example.battleship.configuration;

import com.example.battleship.lobby.gameroom.entity.GameRoom;
import com.example.battleship.lobby.gameroom.repository.GameRoomRepository;
import com.example.battleship.lobby.player.entity.Player;
import com.example.battleship.lobby.player.repository.PlayerRepository;
import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration class for H2 database.
 * It configures the connection between repositories and entities,
 * and their corresponding datasource.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackageClasses = {
        PlayerRepository.class,
        GameRoomRepository.class
    },
    entityManagerFactoryRef = "h2EntityManagerFactory",
    transactionManagerRef = "h2TransactionManager"
)
public class H2JpaConfiguration {

  /**
   * Prepares bean of Local Container Entity Manager Factory for H2 datasource.
   *
   * @param dataSource DataSource H2 datasource declared in
   *                  {@link MultipleDataSourceConfiguration}.
   * @param builder EntityManagerFactoryBuilder.
   * @return LocalContainerEntityManagerFactoryBean.
   */
  @Bean
  public LocalContainerEntityManagerFactoryBean h2EntityManagerFactory(
          @Qualifier("h2DataSource") DataSource dataSource,
          EntityManagerFactoryBuilder builder
  ) {

    return builder
            .dataSource(dataSource)
            .packages(Player.class, GameRoom.class)
            .build();
  }

  /**
   * Prepares bean of Jpa Transaction Manager for H2 database.
   *
   * @param entityManagerFactory EntityManagerFactory.
   * @return JpaTransactionManager.
   */
  @Bean
  public JpaTransactionManager h2TransactionManager(
          @Qualifier("h2EntityManagerFactory") EntityManagerFactory entityManagerFactory
  ) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);

    return transactionManager;
  }
}
