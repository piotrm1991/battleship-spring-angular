package com.example.battleship.security.request;

/**
 * A record representing request for new access token.
 * Contains refresh token.
 */
public record RefreshTokenRequest(
        String refreshToken
) {
}
