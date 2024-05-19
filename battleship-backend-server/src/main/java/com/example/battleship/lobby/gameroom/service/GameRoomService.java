package com.example.battleship.lobby.gameroom.service;

import com.example.battleship.lobby.gameroom.response.GameRoomResponse;
import com.example.battleship.lobby.message.UserMessage;
import com.example.battleship.lobby.player.entity.Player;
import java.io.IOException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for game room related operations.
 */
public interface GameRoomService {

  /**
   * Creates new game room with currently logged-in player.
   *
   * @return GameRoomResponse record of created game room.
   */
  GameRoomResponse createNewGameRoom();

  /**
   * Gets Page of game rooms, with only one player.
   *
   * @param pageable Pageable pagination information.
   * @return Page of GameRoomResponse records.
   */
  Page<GameRoomResponse> getGameRoomsWithOnePlayer(Pageable pageable);

  /**
   * Checks if game room with given player exists.
   *
   * @param player Player object.
   * @return boolean TRUE if exists, FALSE if not.
   */
  boolean checkIfGameRoomWithPlayerExists(Player player);

  /**
   * Handles websocket event of player connecting to game room.
   *
   * @param player Player object.
   * @throws IOException thrown on websocket error.
   */
  void handleConnectPlayerToGameRoom(Player player) throws IOException;

  /**
   * Handles websocket event of player disconnecting from game room.
   *
   * @param player Player object.
   * @throws IOException thrown on websocket error.
   */
  void handleDisconnectPlayerFromGameRoom(Player player) throws IOException;

  /**
   * Handles HTTP request of player to connect to given game room.
   *
   * @param id Long game room id.
   * @return GameRoomResponse record of connected game room.
   */
  GameRoomResponse connectToGameRoom(Long id);

  /**
   * Handles HTTP request of disconnecting player from game room.
   *
   * @param id Long game room id.
   * @throws IOException thrown on websocket message error.
   */
  void handlePlayerLeavingGameRoom(Long id) throws IOException;

  /**
   * Handles Websocket chat message event.
   *
   * @param userMessage UserMessage record, contains chat message.
   * @param playerFrom Player object, sender.
   * @throws IOException thrown on websocket error.
   */
  void handleChatMessageFromPlayer(UserMessage userMessage, Player playerFrom) throws IOException;
}
