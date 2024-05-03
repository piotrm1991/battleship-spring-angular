package com.example.battleship.security.service;

import com.example.battleship.security.entity.RefreshToken;
import com.example.battleship.security.response.RefreshTokenResponse;

public interface RefreshTokenService {

  RefreshToken createRefreshToken(String login);

  RefreshTokenResponse getNewTokenByRefreshToken(String token);

  RefreshToken verifyExpiration(RefreshToken token);

  void deleteRefreshToken(String refreshToken);
}
