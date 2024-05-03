package com.example.battleship.security.service.impl;

import com.example.battleship.exception.ExpiredRefreshTokenException;
import com.example.battleship.exception.RefreshTokenAlreadyDeletedException;
import com.example.battleship.exception.RefreshTokenNotFoundException;
import com.example.battleship.security.entity.RefreshToken;
import com.example.battleship.security.repository.RefreshTokenRepository;
import com.example.battleship.security.response.RefreshTokenResponse;
import com.example.battleship.security.service.JwtService;
import com.example.battleship.security.service.RefreshTokenService;
import com.example.battleship.user.entity.User;
import com.example.battleship.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

  private final RefreshTokenRepository refreshTokenRepository;

  private final UserService userService;

  private final JwtService jwtService;

  @Override
  public RefreshToken createRefreshToken(String login) {
    log.info("Creating refresh token for user with login: {}", login);

    User user = userService.getUserByLogin(login);
    if (refreshTokenRepository.findByUserId(user.getId()).isPresent()) {
      delete(refreshTokenRepository.findByUserId(user.getId()).get());
    }

    RefreshToken refreshToken = RefreshToken.builder()
            .user(userService.getUserByLogin(login))
            .token(UUID.randomUUID().toString())
            .expiryDate(Instant.now().plusMillis(600000)) // set expiry of refresh token to 10 minutes - you can configure it application.properties file
            .build();

    return refreshTokenRepository.save(refreshToken);
  }

  @Override
  public RefreshTokenResponse getNewTokenByRefreshToken(String token) {
    log.info("Getting new refresh token.");

    return refreshTokenRepository.findByToken(token)
            .map(this::verifyExpiration)
            .map(RefreshToken::getUser)
            .map(user -> {
              String accessToken = jwtService.generateToken(user);
              return new RefreshTokenResponse(
                      accessToken
              );
            }).orElseThrow(() ->new RefreshTokenNotFoundException("Refresh Token is not in DB..!!"));
  }

  @Override
  public RefreshToken verifyExpiration(RefreshToken token) {
    log.info("Verifying expiration date of refresh token.");

    if(token.getExpiryDate().compareTo(Instant.now())<0){
      refreshTokenRepository.delete(token);
      throw new ExpiredRefreshTokenException(token.getToken() + " Refresh token is expired. Please make a new login..!");
    }

    return token;
  }

  private void delete(RefreshToken refreshToken) {
    log.info("Deleting refresh token: {}", refreshToken.getToken());

    refreshTokenRepository.delete(refreshToken);
  }

  @Override
  public void deleteRefreshToken(String token) {
    log.info("Deleting refresh token on logout");

    RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> new RefreshTokenAlreadyDeletedException(token + " This refresh token was already deleted."));
    delete(refreshToken);
  }
}
