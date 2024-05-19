package com.example.battleship.lobby.player.repository;

import com.example.battleship.lobby.player.entity.Player;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing database operations on Player entities.
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

  /**
   * Find Player by name.
   *
   * @param name String player name.
   * @return Optional of Player.
   */
  Optional<Player> findByName(String name);

  /**
   * Find Player by uer id.
   *
   * @param id Long user id.
   * @return Optional of Player.
   */
  Optional<Player> findByUserId(Long id);
}
