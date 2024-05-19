package com.example.battleship.lobby.gameroom.controller;

import com.example.battleship.lobby.gameroom.response.GameRoomResponse;
import com.example.battleship.lobby.gameroom.service.GameRoomService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class handling game room related HTTP requests.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/gameRoom")
public class GameRoomController {

  private final GameRoomService gameRoomService;

  /**
   * HTTP POST request for creating new game room
   * by currently logged-in user.
   *
   * @return GameRoomResponse record.
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public GameRoomResponse createGameRoom() {
    log.info("POST-request: creating new Game Room.");

    return gameRoomService.createNewGameRoom();
  }

  /**
   * HTTP POST request for connecting currently logged-in
   * user to the game room.
   *
   * @param id Long game room id.
   * @return GameRoomResponse record.
   */
  @PostMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public GameRoomResponse connectToGameRoom(@PathVariable Long id) {
    log.info("POST-request: connecting to game room.");

    return gameRoomService.connectToGameRoom(id);
  }

  /**
   * HTTP GET request for getting available rooms.
   *
   * @param pageable Pageable pagination information.
   * @return Page of GameRoomResponse records.
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public Page<GameRoomResponse> getGameRoomsWithOnePlayer(
          @PageableDefault(size = 5) Pageable pageable
  ) {
    log.info("GET-request: getting game rooms with one player.");

    return gameRoomService.getGameRoomsWithOnePlayer(pageable);
  }

  /**
   * HTTP DELETE request for disconnected from game room.
   * In case of disconnecting the player that created the room
   * it also deleted it.
   *
   * @param id Long game room id.
   * @throws IOException in case of error during sending websocket message.
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public void deletePlayerFromGameRoom(@PathVariable Long id) throws IOException {
    log.info("DELETE-request: disconnecting player from game room.");

    gameRoomService.handlePlayerLeavingGameRoom(id);
  }
}
