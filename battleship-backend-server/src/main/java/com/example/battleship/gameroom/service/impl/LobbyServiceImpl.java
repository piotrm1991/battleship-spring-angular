package com.example.battleship.gameroom.service.impl;

import com.example.battleship.gameroom.service.LobbyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
@RequiredArgsConstructor
@Slf4j
public class LobbyServiceImpl implements LobbyService {

//  private final PlayerInLobbyList playersList;

  @Override
  public void handleNewPlayerInLobby(WebSocketSession session) {


  }

  @Override
  public void handleExitOfPlayerFromLobby(WebSocketSession session, CloseStatus status) {

  }

  @Override
  public void handleMessageFromPlayer(WebSocketSession session, TextMessage textMessage) {

  }
}
