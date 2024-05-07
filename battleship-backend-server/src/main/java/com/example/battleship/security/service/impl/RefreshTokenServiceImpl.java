package com.example.battleship.security.service.impl;

import com.example.battleship.security.entity.RefreshToken;
import com.example.battleship.security.service.JwtService;
import com.example.battleship.exception.ExpiredRefreshTokenException;
import com.example.battleship.exception.RefreshTokenNotFoundException;
import com.example.battleship.security.repository.RefreshTokenRepository;
import com.example.battleship.security.request.RefreshTokenRequest;
import com.example.battleship.security.response.RefreshTokenResponse;
import com.example.battleship.security.service.RefreshTokenService;
import com.example.battleship.user.entity.User;
import com.example.battleship.user.service.UserService;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * Implementation of RefreshTokenService interface for managing refresh token requests.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

  private final RefreshTokenRepository refreshTokenRepository;
  private final UserService userService;
  private final JwtService jwtService;
  private final BCryptPasswordEncoder passwordEncoder;

  @Override
  public String createRefreshToken(String login) {
    log.info("Creating refresh token for user with login: {}", login);

    User user = userService.getUserByLogin(login);
    if (refreshTokenRepository.findByUserId(user.getId()).isPresent()) {
      delete(refreshTokenRepository.findByUserId(user.getId()).get());
    }

    String token = UUID.randomUUID().toString();

    RefreshToken refreshToken = RefreshToken.builder()
            .user(user)
            .token(passwordEncoder.encode(token))
            .expiryDate(Instant.now().plusMillis(600000))
            .build();

    refreshTokenRepository.save(refreshToken);

    return token;
  }

  @Override
  public RefreshTokenResponse getNewTokenByRefreshToken(RefreshTokenRequest refreshTokenRequest) {
    log.info("Getting new access token by refresh token.");

    return getRefreshTokenByToken(refreshTokenRequest.refreshToken())
          .map(this::verifyExpiration)
          .map(RefreshToken::getUser)
          .map(user -> {
            String accessToken = jwtService.generateToken(user);
            return new RefreshTokenResponse(
                    accessToken
            );
          })
          .orElseThrow(
                () -> new RefreshTokenNotFoundException("Refresh Token is not in DB..!!"));
  }

  private RefreshToken verifyExpiration(RefreshToken token) {
    log.info("Verifying expiration date of refresh token.");

    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      throw new ExpiredRefreshTokenException(
              token.getToken()
              + " Refresh token is expired. Please make a new login..!");
    }

    return token;
  }

  private boolean verifyRefreshToken(RefreshToken refreshToken, String token) {
    log.info("Verifying refresh token.");

    return passwordEncoder.matches(token, refreshToken.getToken());
  }

  private void delete(RefreshToken refreshToken) {
    log.info("Deleting refresh token.");

    refreshTokenRepository.delete(refreshToken);
  }

  private Optional<RefreshToken> getRefreshTokenByToken(String token) {
    log.info("Getting refresh token by token: {}", token);

    return refreshTokenRepository.findAll()
          .stream()
          .filter(ref -> verifyRefreshToken(ref, token))
          .findFirst();
  }

  private RefreshToken getRefreshTokenByUser(User user) {
    log.info("Getting refresh token by user with login: {}", user.getLogin());

    return refreshTokenRepository
            .findByUserId(user.getId())
            .orElseThrow(() -> new RefreshTokenNotFoundException("Refresh token not found."));
  }

  @Override
  public void deleteRefreshToken(String token) {
    log.info("Deleting refresh token on logout");

    User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    delete(getRefreshTokenByUser(currentUser));
  }
}
