package com.example.battleship.gameroom.controller;

import com.example.battleship.gameroom.service.LobbyService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LobbyHandler extends TextWebSocketHandler {

  private final LobbyService lobbyService;

  @Override
  public void afterConnectionEstablished(@NonNull WebSocketSession session) {
    log.info("Connection of new player to the lobby.");
    lobbyService.handleNewPlayerInLobby(session);
  }

  @Override
  protected void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) {
    log.info("New message from player.");
    lobbyService.handleMessageFromPlayer(session, message);
  }

  @Override
  public void afterConnectionClosed(@NonNull WebSocketSession session,@NonNull CloseStatus status) {
    log.info("Player leaves lobby.");
    lobbyService.handleExitOfPlayerFromLobby(session, status);
  }
}
