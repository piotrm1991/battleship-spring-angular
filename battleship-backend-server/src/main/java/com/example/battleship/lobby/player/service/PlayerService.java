package com.example.battleship.lobby.player.service;

import com.example.battleship.lobby.player.entity.Player;
import com.example.battleship.user.entity.User;
import org.springframework.web.socket.WebSocketSession;

public interface PlayerService {

  Player createNewPlayer(WebSocketSession session);
  Player createNewPlayer(User user);

  Player getPlayerByName(String name);

  Player getPlayerFromSession(WebSocketSession session);

  Player setPlayerSession(Player player, String sessionId);

  Player setPlayerStatusOffline(Player player);
  Player setPlayerStatusOnline(Player player);
  Player getOrCreatePlayer(User user);
}
