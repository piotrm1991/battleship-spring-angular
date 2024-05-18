package com.example.battleship.globalchat.service.impl;

import java.util.ArrayList;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

//TODO:Maybe In Memory Database? look for a way to change it
/**
 * In memory list of sessions of users,
 * that are currently connected to global chat.
 */
@Component
public class GlobalChatWebsocketSessionsList extends ArrayList<WebSocketSession> {}
