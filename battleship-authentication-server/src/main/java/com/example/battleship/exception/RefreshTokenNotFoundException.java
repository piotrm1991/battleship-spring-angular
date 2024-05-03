package com.example.battleship.exception;

import java.io.Serial;

public class RefreshTokenNotFoundException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 254695445861295816L;

  public RefreshTokenNotFoundException(String message) {
    super(message);
  }
}
