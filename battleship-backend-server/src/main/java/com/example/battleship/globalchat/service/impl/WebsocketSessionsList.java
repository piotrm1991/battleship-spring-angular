package com.example.battleship.globalchat.service.impl;

import java.util.ArrayList;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

//TODO:In memory database after splitting for resource and authentication server
/**
 * In memory list of sessions of users,
 * that are currently connected to global chat.
 */
@Component
public class WebsocketSessionsList extends ArrayList<WebSocketSession> {}
