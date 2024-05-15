package com.example.battleship.gameroom.configuration;

import com.example.battleship.gameroom.controller.LobbyHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Configuration class for game lobby websocket.
 */
@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketLobbyConfiguration implements WebSocketConfigurer {
  private static final String LOBBY_ENDPOINT = "/lobby";

  private final LobbyHandler lobbyHandler;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(lobbyHandler, LOBBY_ENDPOINT).setAllowedOrigins("*");
  }
}
