package com.example.battleship.lobby.message;

import com.example.battleship.lobby.enums.LobbyMessageType;

public record LobbyMessage(
        LobbyMessageType type,
        String content,
        String sender,

        String time,

        String receiver
) {
}
