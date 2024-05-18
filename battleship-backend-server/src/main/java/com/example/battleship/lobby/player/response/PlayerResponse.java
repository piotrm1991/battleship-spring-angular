package com.example.battleship.lobby.player.response;

import com.example.battleship.lobby.player.enums.PlayerStatus;

public record PlayerResponse(
        String name,
        PlayerStatus status
) {
}
