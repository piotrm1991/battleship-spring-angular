package com.example.battleship.security.response;

public record LoginResponse(
      String token,
      String refreshToken
){}
