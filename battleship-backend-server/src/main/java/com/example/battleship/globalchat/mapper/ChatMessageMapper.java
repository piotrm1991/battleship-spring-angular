package com.example.battleship.globalchat.mapper;

import com.example.battleship.globalchat.model.ChatMessage;
import com.example.battleship.globalchat.model.MessageType;
import com.example.battleship.util.DateTimeConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for mapping and preparing chat messages.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ChatMessageMapper {

  private final ObjectMapper mapper;

  /**
   * Method writes chat message to string.
   *
   * @param message ChatMessage record.
   * @return String chat messages as string.
   */
  public String writeChatMessageAsString(ChatMessage message) {
    log.info("Writing chat message record as string.");
    try {

      return mapper.writeValueAsString(message);
    } catch (JsonProcessingException e) {

      throw new RuntimeException(
              "Error while writing chat message record as string: " + e.getMessage(), e);
    }
  }

  /**
   * Method prepares chat message record according to given data.
   *
   * @param type MessageType enum.
   * @param sender String sender.
   * @param message String message content.
   * @return ChatMessage record.
   */
  public ChatMessage prepareChatMessageRecord(MessageType type, String sender, String message) {
    log.info("Preparing chat message.");

    return new ChatMessage(
            type,
            message,
            sender,
            LocalTime.now().format(DateTimeConstants.TIME_FORMATTER),
            ""
    );
  }
}
