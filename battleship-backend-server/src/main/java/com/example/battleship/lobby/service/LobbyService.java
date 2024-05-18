package com.example.battleship.lobby.service;

import java.io.IOException;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public interface LobbyService {

  void handleNewPlayerInLobby(WebSocketSession session) throws IOException;

  void handleExitOfPlayerFromLobby(WebSocketSession session, CloseStatus status) throws IOException;

  void handleMessageFromPlayer(WebSocketSession session, TextMessage textMessage) throws IOException;
}
