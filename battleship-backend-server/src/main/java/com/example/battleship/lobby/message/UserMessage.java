package com.example.battleship.lobby.message;

import com.example.battleship.lobby.enums.LobbyMessageType;

public record UserMessage(
        LobbyMessageType type,
        String content
) {
}
