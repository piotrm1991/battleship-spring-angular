package com.example.battleship.globalchat.model;

/**
 * Record representing chat message in global chat.
 */
public record ChatMessage(

     MessageType type,
     String content,
     String sender,

     String time,

     String receiver
) { }
