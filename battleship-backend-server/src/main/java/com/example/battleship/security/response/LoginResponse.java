package com.example.battleship.security.response;

/**
 * A record representing response for successful logging in.
 * Contains access adn refresh accessToken.
 */
public record LoginResponse(
      String accessToken,
      String refreshToken
){}
