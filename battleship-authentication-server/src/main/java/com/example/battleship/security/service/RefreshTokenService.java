package com.example.battleship.security.service;

import com.example.battleship.security.entity.RefreshToken;
import com.example.battleship.security.request.RefreshTokenRequest;
import com.example.battleship.security.response.RefreshTokenResponse;
import org.springframework.security.core.Authentication;

public interface RefreshTokenService {

  String createRefreshToken(String login);

  RefreshTokenResponse getNewTokenByRefreshToken(RefreshTokenRequest token);

  RefreshToken verifyExpiration(RefreshToken token);

  void deleteRefreshToken(String refreshToken);
}
