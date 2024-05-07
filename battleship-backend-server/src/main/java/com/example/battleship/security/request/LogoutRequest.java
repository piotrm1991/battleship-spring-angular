package com.example.battleship.security.request;

/**
 * A record representing logout request.
 * Contains refresh token.
 */
public record LogoutRequest(
        String refreshToken
) {
}
