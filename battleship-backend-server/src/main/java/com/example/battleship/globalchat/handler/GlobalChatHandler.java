package com.example.battleship.globalchat.handler;

import com.example.battleship.globalchat.service.GlobalChatService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Handler responsible for operations in global chat.
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class GlobalChatHandler extends TextWebSocketHandler {

  private final GlobalChatService globalChatService;

  @Override
  public void afterConnectionEstablished(@NonNull WebSocketSession session) {
    log.info("Handling new user joining the chat.");
    globalChatService.handleNewUserJoinsGlobalChat(session);
  }

  @Override
  protected void handleTextMessage(
          @NonNull WebSocketSession session,
          @NonNull TextMessage message
  ) {
    log.info("Handling new message in global chat.");
    globalChatService.handleNewGlobalChatMessage(session, message);
  }

  @Override
  public void afterConnectionClosed(
          @NonNull WebSocketSession session,
          @NonNull CloseStatus status
  ) {
    log.info("Handling user leaving the chat.");
    globalChatService.handleUserLeavesGlobalChat(session, status);
  }
}
