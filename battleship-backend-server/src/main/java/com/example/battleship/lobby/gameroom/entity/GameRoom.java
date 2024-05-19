package com.example.battleship.lobby.gameroom.entity;

import com.example.battleship.lobby.player.entity.Player;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing private game room in the system.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class GameRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @OneToOne
  private Player playerOne;

  @OneToOne
  private Player playerTwo;
}
