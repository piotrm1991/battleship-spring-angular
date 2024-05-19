package com.example.battleship.lobby.gameroom.repository;

import com.example.battleship.lobby.gameroom.entity.GameRoom;
import com.example.battleship.lobby.player.entity.Player;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing database operations on GameRoom entities.
 */
@Repository
public interface GameRoomRepository extends JpaRepository<GameRoom, Long> {

  /**
   * Finds game rooms with given second player.
   * User for finding game room that doesn't have
   * a second payer.
   *
   * @param pageable Pageable pagination information.
   * @param player Player object to search.
   * @return Page of GameRoom objects.
   */
  Page<GameRoom> findByPlayerTwo(Pageable pageable, Player player);

  /**
   * Finds game room by player one id.
   *
   * @param id Long player id.
   * @return Optional of GameRoom entity.
   */
  Optional<GameRoom> findByPlayerOneId(Long id);

  /**
   * Finds game room by player two id.
   *
   * @param id Long player id.
   * @return Optional of GameRoom entity.
   */
  Optional<GameRoom> findByPlayerTwoId(Long id);

  /**
   * Checks if game room by player one id, exists.
   *
   * @param id Long player id.
   * @return boolean TRUE if exists, FALSE if not.
   */
  boolean existsByPlayerOneId(Long id);

  /**
   * Checks if game room by player two id, exists.
   *
   * @param id Long player id.
   * @return boolean TRUE if exists, FALSE if not.
   */
  boolean existsByPlayerTwoId(Long id);
}
