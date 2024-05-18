package com.example.battleship.lobby.mapper;

import com.example.battleship.lobby.message.LobbyMessage;
import com.example.battleship.lobby.message.UserMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class LobbyMessageMapper {

  private final ObjectMapper mapper;

  /**
   * Method writes lobby message to string.
   *
   * @param message LobbyMessage record.
   * @return String chat messages as string.
   */
  public String writeLobbyMessageAsString(LobbyMessage message) {
    log.info("Writing lobby message record as string.");
    try {

      return mapper.writeValueAsString(message);
    } catch (JsonProcessingException e) {

      throw new RuntimeException(
              "Error while writing lobby message record as string: " + e.getMessage(), e);
    }
  }

  public UserMessage mapPayloadToUserMessage(String payload) {
    log.info("Writing payload to user message record.");
    try {

      return mapper.readValue(payload, UserMessage.class);
    } catch (JsonProcessingException e) {

      throw new RuntimeException(
              "Error while writing payload as user message record: " + e.getMessage(), e);
    }
  }
}
