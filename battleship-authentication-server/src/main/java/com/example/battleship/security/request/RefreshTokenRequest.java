package com.example.battleship.security.request;

public record RefreshTokenRequest(
        String refreshToken,
        String token
) {
}
