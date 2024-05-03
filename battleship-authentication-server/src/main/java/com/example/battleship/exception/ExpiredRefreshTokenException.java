package com.example.battleship.exception;

import java.io.Serial;

public class ExpiredRefreshTokenException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 2416668624067346574L;

  public ExpiredRefreshTokenException(String message) {
    super(message);
  }
}
