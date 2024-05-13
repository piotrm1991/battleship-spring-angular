package com.example.battleship.globalchat.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;

//TODO: fix websocket security.
/**
 * Configuration class for websocket security.
 */
@Configuration
@EnableWebSocketSecurity
public class WebSocketSecurityConfiguration {

  @Bean
  AuthorizationManager<Message<?>> messageAuthorizationManager(
          MessageMatcherDelegatingAuthorizationManager.Builder messages
  ) {

    return AuthorityAuthorizationManager.hasAnyRole("USER", "ADMIN");
  }
}
