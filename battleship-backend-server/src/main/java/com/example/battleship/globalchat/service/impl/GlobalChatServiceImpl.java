package com.example.battleship.globalchat.service.impl;

import static com.example.battleship.util.GlobalChatMessages.getMessageNewUserJoinsChat;
import static com.example.battleship.util.GlobalChatMessages.getMessageUserLeavesChat;

import com.example.battleship.globalchat.mapper.ChatMessageMapper;
import com.example.battleship.globalchat.model.ChatMessage;
import com.example.battleship.globalchat.model.MessageType;
import com.example.battleship.globalchat.service.GlobalChatService;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Service implementation for global chat related operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GlobalChatServiceImpl implements GlobalChatService {

  private final WebsocketSessionsList webSocketSessions;
  private final ChatMessageMapper mapper;

  @Override
  public void handleNewUserJoinsGlobalChat(WebSocketSession session) {
    log.info("Sending new connection message.");
    webSocketSessions.add(session);
    sendMessageToAllSessions(prepareNewConnectionMessage(session));
  }

  @Override
  public void handleNewGlobalChatMessage(WebSocketSession session, TextMessage message) {
    log.info("Sending a message from user to global chat!");
    sendMessageToAllSessions(prepareMessageFromUser(session, message));
  }

  @Override
  public void handleUserLeavesGlobalChat(WebSocketSession session, CloseStatus status) {
    log.info("Sending delete connection message.");
    webSocketSessions.remove(session);
    sendMessageToAllSessions(prepareDeleteConnectionMessage(session));
  }

  private TextMessage prepareNewConnectionMessage(WebSocketSession session) {
    String userLogin = Objects.requireNonNull(session.getPrincipal()).getName();
    log.info("Preparing message new user {} joins the chat.", userLogin);
    ChatMessage chatMessage = mapper
            .prepareChatMessageRecord(
                    MessageType.CONNECT,
                    "SYSTEM",
                    getMessageNewUserJoinsChat(userLogin)
            );

    return new TextMessage(mapper.writeChatMessageAsString(chatMessage));
  }

  private TextMessage prepareDeleteConnectionMessage(WebSocketSession session) {
    String userLogin = Objects.requireNonNull(session.getPrincipal()).getName();
    log.info("Preparing message user {} leaves the chat.", userLogin);
    ChatMessage chatMessage = mapper
            .prepareChatMessageRecord(
                    MessageType.DISCONNECT,
                    "SYSTEM",
                    getMessageUserLeavesChat(userLogin));

    return new TextMessage(mapper.writeChatMessageAsString(chatMessage));
  }

  private TextMessage prepareMessageFromUser(WebSocketSession session, TextMessage messageText) {
    String userLogin = Objects.requireNonNull(session.getPrincipal()).getName();
    log.info("Preparing message from {} for global chat.", userLogin);
    ChatMessage chatMessage = mapper
            .prepareChatMessageRecord(
                    MessageType.CHAT,
                    userLogin,
                    messageText.getPayload());

    return new TextMessage(mapper.writeChatMessageAsString(chatMessage));
  }

  private void sendMessageToAllSessions(TextMessage message) {
    log.info("Sending message to all sessions.");
    webSocketSessions.forEach(ses -> {
      try {
        ses.sendMessage(message);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }
}
