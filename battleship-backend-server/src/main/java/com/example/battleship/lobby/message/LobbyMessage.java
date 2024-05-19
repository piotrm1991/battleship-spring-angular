package com.example.battleship.lobby.message;

import com.example.battleship.lobby.enums.LobbyMessageType;

/**
 * Record representing messages send from lobby to users.
 */
public record LobbyMessage(
        LobbyMessageType type,
        String content,
        String sender,

        String time
) {
}
