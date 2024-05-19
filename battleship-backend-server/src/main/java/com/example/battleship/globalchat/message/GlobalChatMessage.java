package com.example.battleship.globalchat.message;

import com.example.battleship.globalchat.enums.GlobalChatMessageType;

/**
 * Record representing chat message in global chat.
 */
public record GlobalChatMessage(

     GlobalChatMessageType type,
     String content,
     String sender,

     String time
) { }
