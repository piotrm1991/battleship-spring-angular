package com.example.battleship.security.request;

public record LogoutRequest(
        String refreshToken
) {
}
