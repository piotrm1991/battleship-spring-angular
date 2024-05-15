package com.example.battleship.globalchat.configuration;

import com.example.battleship.globalchat.handler.GlobalChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Configuration class for global chat websocket.
 */
@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketMessageConfiguration implements WebSocketConfigurer {
  private static final String CHAT_ENDPOINT = "/chat";

  private final GlobalChatHandler globalChatHandler;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(globalChatHandler, CHAT_ENDPOINT).setAllowedOrigins("*");
  }
}
