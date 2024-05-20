package com.example.battleship.globalchat.mapper;

import com.example.battleship.globalchat.enums.GlobalChatMessageType;
import com.example.battleship.globalchat.message.GlobalChatMessage;
import com.example.battleship.util.DateTimeConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for mapping global chat messages.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class GlobalChatMessageMapper {

  private final ObjectMapper mapper;

  /**
   * Method writes global chat message to string.
   *
   * @param message GlobalChatMessage record.
   * @return String chat messages as string.
   */
  public String writeChatMessageAsString(GlobalChatMessage message) {
    log.info("Writing global chat message record as string.");
    try {

      return mapper.writeValueAsString(message);
    } catch (JsonProcessingException e) {

      throw new RuntimeException(
              "Error while writing global chat message record to string: " + e.getMessage(), e);
    }
  }
}
