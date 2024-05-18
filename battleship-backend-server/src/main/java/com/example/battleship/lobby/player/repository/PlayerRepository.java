package com.example.battleship.lobby.player.repository;

import java.util.Optional;
import com.example.battleship.lobby.player.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
  Optional<Player> findByName(String name);

  Optional<Player> findByUserId(Long id);
}
