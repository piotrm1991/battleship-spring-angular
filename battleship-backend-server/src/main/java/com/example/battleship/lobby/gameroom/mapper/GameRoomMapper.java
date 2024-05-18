package com.example.battleship.lobby.gameroom.mapper;

import com.example.battleship.lobby.gameroom.entity.GameRoom;
import com.example.battleship.lobby.gameroom.response.GameRoomResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class GameRoomMapper {

  private final ObjectMapper mapper;

  public GameRoomResponse mapEntityToResponse(GameRoom gameRoom) {
    log.info("Mapping Game Room to game room response.");
    try {

      return mapper.readValue(mapper.writeValueAsString(gameRoom), GameRoomResponse.class);
    } catch (JsonProcessingException e) {

      throw new RuntimeException(
              "Error while mapping game room entity to response " + e.getMessage(), e);
    }
  }
}
