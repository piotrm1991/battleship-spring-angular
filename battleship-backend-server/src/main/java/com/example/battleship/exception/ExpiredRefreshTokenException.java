package com.example.battleship.exception;

import java.io.Serial;

/**
 * This exception is thrown when refresh accessToken has expired.
 * This typically occurs when a user tries to refresh access accessToken
 * with refresh accessToken that has already expired.
 */
public class ExpiredRefreshTokenException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 2416668624067346574L;

  public ExpiredRefreshTokenException(String message) {
    super(message);
  }
}
