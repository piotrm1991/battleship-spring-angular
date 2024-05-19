package com.example.battleship.lobby.player.response;

import com.example.battleship.lobby.player.enums.PlayerStatus;

/**
 * Response record of Player entity.
 */
public record PlayerResponse(
        String name,
        PlayerStatus status
) {
}
