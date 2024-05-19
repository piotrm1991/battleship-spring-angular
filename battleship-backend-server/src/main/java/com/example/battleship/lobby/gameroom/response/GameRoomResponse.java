package com.example.battleship.lobby.gameroom.response;

import com.example.battleship.lobby.player.response.PlayerResponse;

/**
 * Record represents game room information.
 */
public record GameRoomResponse(
        Long id,
        PlayerResponse playerOne,
        PlayerResponse playerTwo
) {
}
