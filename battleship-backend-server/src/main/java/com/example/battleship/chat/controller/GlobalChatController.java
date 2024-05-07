package com.example.battleship.chat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Controller
public class GlobalChatController implements WebSocketHandler {

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    log.info("Connection established on session: {}", session.getId());
    System.out.println(session);
  }

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    log.info("Message: {}", message.getPayload());
    session.sendMessage(new TextMessage((String) message.getPayload()));
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    log.info("Exception occurred: {} on session: {}", exception.getMessage(), session.getId());
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
    log.info("Connection closed on session: {} with status: {}", session.getId(), closeStatus.getCode());
  }

  @Override
  public boolean supportsPartialMessages() {
    return false;
  }
}
