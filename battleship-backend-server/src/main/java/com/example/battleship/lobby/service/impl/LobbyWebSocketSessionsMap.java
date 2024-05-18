package com.example.battleship.lobby.service.impl;

import java.util.HashMap;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class LobbyWebSocketSessionsMap extends HashMap<String, WebSocketSession> {
}
