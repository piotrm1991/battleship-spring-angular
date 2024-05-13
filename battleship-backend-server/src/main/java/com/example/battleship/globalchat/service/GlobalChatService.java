package com.example.battleship.globalchat.service;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Service interface for global chat related operations.
 */
public interface GlobalChatService {

  /**
   * Method handles event of new user joining chat.
   *
   * @param session WebSocketSession.
   */
  void handleNewUserJoinsGlobalChat(WebSocketSession session);

  /**
   * Method handles event of user sending message to global chat.
   *
   * @param session WebSocketSession.
   * @param message TextMessage.
   */
  void handleNewGlobalChatMessage(WebSocketSession session, TextMessage message);

  /**
   * Method handles event of user leaving chat.
   *
   * @param session WebSocketSession.
   * @param status CloseStatus.
   */
  void handleUserLeavesGlobalChat(WebSocketSession session, CloseStatus status);
}
