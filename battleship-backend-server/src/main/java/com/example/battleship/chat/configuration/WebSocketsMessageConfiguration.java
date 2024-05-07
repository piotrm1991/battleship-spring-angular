package com.example.battleship.chat.configuration;


import com.example.battleship.chat.controller.GlobalChatController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketsMessageConfiguration implements WebSocketConfigurer {
  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(testHandler(), "/chat-global").setAllowedOrigins("*");
  }

  @Bean
  WebSocketHandler testHandler() {
    return new GlobalChatController();
  }
}
