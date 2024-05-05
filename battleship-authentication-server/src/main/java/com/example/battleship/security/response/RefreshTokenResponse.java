package com.example.battleship.security.response;

/**
 * A record representing response to request for new access token.
 * Contains new access token.
 */
public record RefreshTokenResponse(
        String accessToken
) {
}
