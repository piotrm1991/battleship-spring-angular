package com.example.battleship.lobby.gameroom.repository;

import java.util.Optional;
import com.example.battleship.lobby.gameroom.entity.GameRoom;
import com.example.battleship.lobby.player.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRoomRepository extends JpaRepository<GameRoom, Long> {

  Page<GameRoom> findByPlayerTwo(Pageable pageable, Player player);
  Optional<GameRoom> findByPlayerOneId(Long id);
  Optional<GameRoom> findByPlayerTwoId(Long id);

  boolean existsByPlayerOneId(Long id);

  boolean existsByPlayerTwoId(Long id);
}
