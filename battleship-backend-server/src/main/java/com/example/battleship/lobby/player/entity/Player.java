package com.example.battleship.lobby.player.entity;

import com.example.battleship.lobby.player.enums.PlayerStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing player in the system.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Player {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column
  private String name;

  @Column
  private Long userId;

  @Column
  private PlayerStatus status;

  private String sessionId;
}
