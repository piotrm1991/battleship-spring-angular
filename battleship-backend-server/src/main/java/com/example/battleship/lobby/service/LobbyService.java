package com.example.battleship.lobby.service;

import java.io.IOException;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Service interface for lobby websocket events.
 */
public interface LobbyService {

  /**
   * Handles connection of new player to the lobby.
   * Connects player to corresponding game room.
   *
   * @param session WebSocketSession of new connection.
   * @throws IOException thrown on websocket error.
   */
  void handleNewPlayerInLobby(WebSocketSession session) throws IOException;

  /**
   * Handles disconnect of player from the lobby.
   *
   * @param session WebSocketSession of connected player.
   * @throws IOException thrown on websocket error.
   */
  void handleExitOfPlayerFromLobby(WebSocketSession session, CloseStatus status) throws IOException;

  /**
   * Handles message from player to the lobby.
   *
   * @param session WebSocketSession of connected player.
   * @throws IOException thrown on websocket error.
   */
  void handleMessageFromPlayer(
          WebSocketSession session,
          TextMessage textMessage
  ) throws IOException;
}
