package com.example.battleship.security.service;

import com.example.battleship.security.request.RefreshTokenRequest;
import com.example.battleship.security.response.RefreshTokenResponse;

/**
 * Service interface for managing refresh token requests.
 */
public interface RefreshTokenService {

  /**
   * Generate new refresh token and save it in database.
   *
   * @param login String User login.
   * @return String refresh token.
   */
  String createRefreshToken(String login);

  /**
   * Generate new access token based on refresh token.
   *
   * @param token RefreshTokenRequest record containing refresh token.
   * @return RefreshTokenResponse containing new access token.
   */
  RefreshTokenResponse getNewTokenByRefreshToken(RefreshTokenRequest token);

  /**
   * Delete refresh token from database.
   *
   * @param refreshToken String refresh token.
   */
  void deleteRefreshToken(String refreshToken);
}
