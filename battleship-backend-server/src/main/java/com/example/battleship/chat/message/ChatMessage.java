package com.example.battleship.chat.message;

public record ChatMessage(
        String content,
        String sender,
        String time,
        String receiver
) {
}
