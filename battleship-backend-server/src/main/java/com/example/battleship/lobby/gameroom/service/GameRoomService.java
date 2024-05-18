package com.example.battleship.lobby.gameroom.service;

import java.io.IOException;
import com.example.battleship.lobby.gameroom.response.GameRoomResponse;
import com.example.battleship.lobby.message.UserMessage;
import com.example.battleship.lobby.player.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GameRoomService {

  GameRoomResponse createNewGameRoom();

  Page<GameRoomResponse> getGameRoomsWithOnePlayer(Pageable pageable);

  boolean checkIfPlayerHasGameRoom(Player player);

  void handleConnectPlayerToGameRoom(Player player) throws IOException;

  void handleDisconnectPlayerFromGameRoom(Player player) throws IOException;

  GameRoomResponse connectToGameRoom(Long id);

  void handlePlayerLeavingGameRoom(Long id) throws IOException;

  void handleChatMessageFromPlayer(UserMessage userMessage, Player playerFrom) throws IOException;
}
