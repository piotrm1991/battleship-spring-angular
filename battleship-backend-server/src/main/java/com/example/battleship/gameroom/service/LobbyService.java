package com.example.battleship.gameroom.service;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public interface LobbyService {

  void handleNewPlayerInLobby(WebSocketSession session);

  void handleExitOfPlayerFromLobby(WebSocketSession session, CloseStatus status);

  void handleMessageFromPlayer(WebSocketSession session, TextMessage textMessage);
}
