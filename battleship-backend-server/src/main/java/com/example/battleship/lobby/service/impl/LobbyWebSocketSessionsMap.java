package com.example.battleship.lobby.service.impl;

import java.util.HashMap;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

/**
 * In memory map of websocket session of users currently connected to the lobby.
 */
@Component
public class LobbyWebSocketSessionsMap extends HashMap<String, WebSocketSession> { }
