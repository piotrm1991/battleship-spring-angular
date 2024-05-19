package com.example.battleship.lobby.player.mapper;

import com.example.battleship.lobby.player.entity.Player;
import com.example.battleship.lobby.player.response.PlayerResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for mapping between Player DTOs.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class PlayerMapper {

  private final ObjectMapper mapper;

  /**
   * Maps a Player entity to PLayerResponse record.
   *
   * @param player The Player entity.
   * @return A PlayerResponse record.
   */
  public PlayerResponse mapEntityToResponse(Player player) {
    log.info("Mapping Player to player response.");
    try {

      return mapper.readValue(mapper.writeValueAsString(player), PlayerResponse.class);
    } catch (JsonProcessingException e) {

      throw new RuntimeException(
              "Error while mapping player entity to response " + e.getMessage(), e);
    }
  }
}
