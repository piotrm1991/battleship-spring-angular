package com.example.battleship.lobby.service.impl;

import java.io.IOException;
import java.util.Objects;
import com.example.battleship.lobby.gameroom.service.GameRoomService;
import com.example.battleship.lobby.mapper.LobbyMessageMapper;
import com.example.battleship.lobby.message.UserMessage;
import com.example.battleship.lobby.player.entity.Player;
import com.example.battleship.lobby.player.service.PlayerService;
import com.example.battleship.lobby.service.LobbyService;
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

  private final LobbyWebSocketSessionsMap webSocketSessionsList;
  private final GameRoomService gameRoomService;
  private final PlayerService playerService;
  private final LobbyMessageMapper lobbyMessageMapper;


  //TODO: check standard for websocket error codes.
  @Override
  public void handleNewPlayerInLobby(WebSocketSession session) throws IOException {
    log.info("Handling connection of player to lobby.");
    Player player = playerService.getPlayerFromSession(session);
    if (!gameRoomService.checkIfPlayerHasGameRoom(player)) {
      session.close(new CloseStatus(CloseStatus.SERVER_ERROR.getCode(), "Player not connected to any Game Room, yet."));
    } else {
      player = playerService.setPlayerSession(player, session.getId());
      webSocketSessionsList.put(session.getId(), session);
      gameRoomService.handleConnectPlayerToGameRoom(player);
    }
  }

  @Override
  public void handleExitOfPlayerFromLobby(WebSocketSession session, CloseStatus status) throws IOException {
    log.info("Handling disconnection of player to lobby.");
    Player player = playerService.getPlayerFromSession(session);
    if (!gameRoomService.checkIfPlayerHasGameRoom(player)) {
      player = playerService.setPlayerSession(player, "");
      gameRoomService.handleDisconnectPlayerFromGameRoom(player);
      webSocketSessionsList.remove(session.getId());
    }
  }

  @Override
  public void handleMessageFromPlayer(WebSocketSession session, TextMessage textMessage) throws IOException {
    String userLogin = Objects.requireNonNull(session.getPrincipal()).getName();
    Player player = playerService.getPlayerFromSession(session);
    log.info("Handling message from player: {}", userLogin);
    UserMessage userMessage = lobbyMessageMapper.mapPayloadToUserMessage(textMessage.getPayload());
    switch (userMessage.type()) {
      case CHAT -> gameRoomService.handleChatMessageFromPlayer(userMessage, player);
    }
  }
}
