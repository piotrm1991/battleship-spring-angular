package com.example.battleship.lobby.gameroom.controller;

import java.io.IOException;
import com.example.battleship.lobby.gameroom.response.GameRoomResponse;
import com.example.battleship.lobby.gameroom.service.GameRoomService;
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

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/gameRoom")
public class GameRoomController {

  private final GameRoomService gameRoomService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public GameRoomResponse createGameRoom() {
    log.info("POST-request: creating new Game Room.");

    return gameRoomService.createNewGameRoom();
  }

  @PostMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public GameRoomResponse connectToGameRoom(@PathVariable Long id) {
    log.info("POST-request: connecting to game room.");

    return gameRoomService.connectToGameRoom(id);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public Page<GameRoomResponse> getGameRoomsWithOnePlayer(@PageableDefault(size = 5) Pageable pageable) {
    log.info("GET-request: getting game rooms with one player.");

    return gameRoomService.getGameRoomsWithOnePlayer(pageable);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public void deletePlayerFromGameRoom(@PathVariable Long id) throws IOException {
    log.info("DELETE-request: disconnecting player from game room.");

    gameRoomService.handlePlayerLeavingGameRoom(id);
  }
}
