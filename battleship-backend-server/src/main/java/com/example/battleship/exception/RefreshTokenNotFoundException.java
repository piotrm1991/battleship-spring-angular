package com.example.battleship.exception;

import java.io.Serial;

/**
 * This exception is thrown when refresh token can not be found in database.
 * This typically occurs when a user tries to refresh access token with
 * wrong or expired refresh token.
 */
public class RefreshTokenNotFoundException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 254695445861295816L;

  public RefreshTokenNotFoundException(String message) {
    super(message);
  }
}
