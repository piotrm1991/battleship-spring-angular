package com.example.battleship.lobby.message;

import com.example.battleship.lobby.enums.LobbyMessageType;

/**
 * Record representing user message to the lobby.
 */
public record UserMessage(
        LobbyMessageType type,
        String content
) { }
