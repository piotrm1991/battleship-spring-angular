package com.example.battleship.lobby.gameroom.service.impl;

import static com.example.battleship.lobby.util.LobbyMessageFactory.generateSystemMessagePlayerConnected;
import static com.example.battleship.lobby.util.LobbyMessageFactory.generateSystemMessagePlayerDisconnected;
import static com.example.battleship.lobby.util.LobbyMessageFactory.prepareLobbyMessageRecord;

import com.example.battleship.exception.EntityNotFoundException;
import com.example.battleship.lobby.enums.LobbyMessageType;
import com.example.battleship.lobby.gameroom.entity.GameRoom;
import com.example.battleship.lobby.gameroom.mapper.GameRoomMapper;
import com.example.battleship.lobby.gameroom.repository.GameRoomRepository;
import com.example.battleship.lobby.gameroom.response.GameRoomResponse;
import com.example.battleship.lobby.gameroom.service.GameRoomService;
import com.example.battleship.lobby.mapper.LobbyMessageMapper;
import com.example.battleship.lobby.message.LobbyMessage;
import com.example.battleship.lobby.message.UserMessage;
import com.example.battleship.lobby.player.entity.Player;
import com.example.battleship.lobby.player.enums.PlayerStatus;
import com.example.battleship.lobby.player.service.PlayerService;
import com.example.battleship.lobby.service.impl.LobbyWebSocketSessionsMap;
import com.example.battleship.user.entity.User;
import com.example.battleship.user.service.UserService;
import com.example.battleship.util.ExceptionMessagesConstants;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Service implementation for game room related operations.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GameRoomServiceImpl implements GameRoomService {

  private final GameRoomRepository gameRoomRepository;
  private final GameRoomMapper gameRoomMapper;
  private final PlayerService playerService;
  private final UserService userService;
  private final LobbyMessageMapper lobbyMessageMapper;
  private final LobbyWebSocketSessionsMap lobbyWebSocketSessionsMap;

  @Override
  @Transactional
  public GameRoomResponse createNewGameRoom() {
    User user = userService.getCurrentlyLoggedUser();

    log.info("Creating new game room for player: {}", user.getLogin());

    Player player = playerService.getOrCreatePlayer(user);
    GameRoom gameRoom = GameRoom.builder()
            .playerOne(player)
            .build();

    return gameRoomMapper.mapEntityToResponse(save(gameRoom));
  }

  @Override
  public Page<GameRoomResponse> getGameRoomsWithOnePlayer(Pageable pageable) {
    log.info("Getting list of game rooms with only one player.");

    return gameRoomRepository
          .findByPlayerTwo(pageable, null)
          .map(gameRoomMapper::mapEntityToResponse);
  }

  @Override
  public void handleConnectPlayerToGameRoom(Player player) throws IOException {
    log.info("Handling connection of player: {}, to game room.", player.getName());

    player = playerService.setPlayerStatus(player, PlayerStatus.ONLINE);
    Optional<GameRoom> gameRoom = getGameRoomByPlayer(player);
    if (gameRoom.isPresent()) {
      handleConnectPlayerPrivateChat(player, gameRoom.get());
      handleConnectPlayerGameBoard(player, gameRoom.get());
    }
  }

  private Optional<GameRoom> getGameRoomByPlayer(Player player) {
    log.info("Getting game room by player: {}", player.getName());

    return (gameRoomRepository.existsByPlayerOneId(player.getId()))
            ? gameRoomRepository.findByPlayerOneId(player.getId())
            : gameRoomRepository.findByPlayerTwoId(player.getId());
  }

  private void handleConnectPlayerGameBoard(Player player, GameRoom gameRoom) {

  }

  private void handleConnectPlayerPrivateChat(Player player, GameRoom gameRoom) throws IOException {
    log.info("Handling connection of player: {}, to private chat.", player.getName());

    LobbyMessage message = generateSystemMessagePlayerConnected(player.getName());
    sendMessage(player, message);
    if (getOtherPlayerForMessage(player, gameRoom) != null) {
      sendMessage(getOtherPlayerForMessage(player, gameRoom), message);
    }
  }

  @Override
  public void handleDisconnectPlayerFromGameRoom(Player player) throws IOException {
    log.info("Handling disconnection of player: {}, from game room.", player.getName());

    player = playerService.setPlayerStatus(player, PlayerStatus.OFFLINE);
    Optional<GameRoom> gameRoom = getGameRoomByPlayer(player);
    if (gameRoom.isPresent()) {
      handleDisconnectPlayerPrivateChat(player, gameRoom.get());
      handleDisconnectPlayerGameBoard(player, gameRoom.get());
    }
  }

  private void handleDisconnectPlayerGameBoard(Player player, GameRoom gameRoom) {

  }

  private void handleDisconnectPlayerPrivateChat(
          Player player,
          GameRoom gameRoom
  ) throws IOException {
    log.info("Handling disconnection of player: {}, from private chat.", player.getName());

    if (getOtherPlayerForMessage(player, gameRoom) != null) {
      LobbyMessage message = generateSystemMessagePlayerDisconnected(player.getName());
      sendMessage(getOtherPlayerForMessage(player, gameRoom), message);
    }
  }

  private void sendMessage(Player player, LobbyMessage message) throws IOException {
    log.info("Sending message to player: {}", player.getName());

    if (!player.getSessionId().isEmpty()) {
      WebSocketSession session = lobbyWebSocketSessionsMap.get(player.getSessionId());
      session.sendMessage(new TextMessage(lobbyMessageMapper.writeLobbyMessageAsString(message)));
    }
  }

  private void delete(GameRoom gameRoom) {
    log.info("Deleting empty game room with id: {}.", gameRoom.getId());

    gameRoomRepository.delete(gameRoom);
  }

  @Override
  public GameRoomResponse connectToGameRoom(Long id) {
    log.info("Connecting player two to game room with id: {}.", id);

    GameRoom gameRoom = getGameRoomById(id);
    User user = userService.getCurrentlyLoggedUser();
    Player player = playerService.getOrCreatePlayer(user);
    gameRoom.setPlayerTwo(player);

    return gameRoomMapper.mapEntityToResponse(save(gameRoom));
  }

  @Override
  public void handlePlayerLeavingGameRoom(Long id) throws IOException {
    log.info("Disconnecting player from game room with id: {}.", id);

    GameRoom gameRoom = getGameRoomById(id);
    User user = userService.getCurrentlyLoggedUser();
    Player player = playerService.getOrCreatePlayer(user);
    if (gameRoom.getPlayerOne().getId().equals(player.getId())) {
      deleteGameRoom(player, gameRoom);
    } else {
      disconnectPlayerTwoFromGameRoom(player, gameRoom);
    }
  }

  @Override
  public void handleChatMessageFromPlayer(
          UserMessage userMessage,
          Player playerFrom
  ) throws IOException {
    log.info("Handling chat message from player: {}", playerFrom.getName());

    Optional<GameRoom> gameRoom = getGameRoomByPlayer(playerFrom);
    if (gameRoom.isPresent()) {
      LobbyMessage lobbyMessage = prepareLobbyMessageRecord(
              LobbyMessageType.CHAT, playerFrom.getName(),
              userMessage.content()
      );
      sendMessage(playerFrom, lobbyMessage);
      if (Objects.nonNull(getOtherPlayerForMessage(playerFrom, gameRoom.get()))) {
        sendMessage(getOtherPlayerForMessage(playerFrom, gameRoom.get()), lobbyMessage);
      }
    }
  }

  private void deleteGameRoom(Player player, GameRoom gameRoom) throws IOException {
    log.info("Handling player one: {}, leaving and deleting game room with id: {}.",
        player.getName(),
        gameRoom.getId()
    );

    handleDisconnectPlayerPrivateChat(player, gameRoom);
    handleDisconnectPlayerGameBoard(player, gameRoom);
    lobbyWebSocketSessionsMap.get(gameRoom.getPlayerOne().getSessionId()).close();
    lobbyWebSocketSessionsMap.remove(gameRoom.getPlayerOne().getSessionId());
    if (Objects.nonNull(gameRoom.getPlayerTwo())) {
      lobbyWebSocketSessionsMap.get(gameRoom.getPlayerTwo().getSessionId()).close();
      lobbyWebSocketSessionsMap.remove(gameRoom.getPlayerTwo().getSessionId());
    }
    delete(gameRoom);
  }

  private void disconnectPlayerTwoFromGameRoom(
          Player player,
          GameRoom gameRoom
  ) throws IOException {
    log.info("Handling player two: {} leaving the game room with id: {}.",
        player.getName(),
        gameRoom.getId()
    );

    handleDisconnectPlayerPrivateChat(player, gameRoom);
    handleDisconnectPlayerGameBoard(player, gameRoom);
    gameRoom.setPlayerTwo(null);
    save(gameRoom);
    WebSocketSession session = lobbyWebSocketSessionsMap.get(player.getSessionId());
    session.close();
    lobbyWebSocketSessionsMap.remove(player.getSessionId());
  }

  private GameRoom save(GameRoom gameRoom) {
    log.info("Saving new game room with player: {}", gameRoom.getPlayerOne().getName());

    return gameRoomRepository.save(gameRoom);
  }

  private GameRoom getGameRoomById(Long id) {
    log.info("Getting game room by id: {}", id);

    return gameRoomRepository
            .findById(id)
            .orElseThrow(() ->
                new EntityNotFoundException(ExceptionMessagesConstants
                    .createEntityNotExistsMessage(
                        GameRoom.class.getSimpleName(),
                        id))
                );
  }

  private Player getOtherPlayerForMessage(Player player, GameRoom gameRoom) {
    log.info("Getting other player from game room for the message.");

    return (gameRoom.getPlayerOne().getId().equals(player.getId()))
            ? gameRoom.getPlayerTwo()
            : gameRoom.getPlayerOne();
  }

  @Override
  public boolean checkIfGameRoomWithPlayerExists(Player player) {
    log.info("Checking if game room of the player: {}, exists.", player.getName());

    return gameRoomRepository.existsByPlayerOneId(player.getId())
        || gameRoomRepository.existsByPlayerTwoId(player.getId());
  }
}
