package com.example.battleship.exception;

import java.io.Serial;

public class RefreshTokenAlreadyDeletedException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 254695049785295816L;

  public RefreshTokenAlreadyDeletedException(String message) {
    super(message);
  }
}
