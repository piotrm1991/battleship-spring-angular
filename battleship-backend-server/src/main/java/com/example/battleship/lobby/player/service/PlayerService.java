package com.example.battleship.lobby.player.service;

import com.example.battleship.lobby.player.entity.Player;
import com.example.battleship.lobby.player.enums.PlayerStatus;
import com.example.battleship.user.entity.User;
import org.springframework.web.socket.WebSocketSession;

/**
 * Service interface for player-related operations.
 */
public interface PlayerService {

  /**
   * Creates player entity based on given user.
   *
   * @param user User.
   * @return Player.
   */
  Player createNewPlayer(User user);

  /**
   * Gets player based on given WebSocketSession.
   *
   * @param session WebSocketSession.
   * @return Player.
   */
  Player getPlayerFromSession(WebSocketSession session);

  /**
   * Sets player's WebSocketSession id.
   *
   * @param player Player.
   * @param sessionId String session id.
   * @return Player.
   */
  Player setPlayerSessionId(Player player, String sessionId);

  /**
   * Sets Player status.
   *
   * @param player Player.
   * @param status PlayerStatus.
   * @return Player.
   */
  Player setPlayerStatus(Player player, PlayerStatus status);

  /**
   * Get existing player entity or create it.
   *
   * @param user User to base the player on.
   * @return Player.
   */
  Player getOrCreatePlayer(User user);
}
