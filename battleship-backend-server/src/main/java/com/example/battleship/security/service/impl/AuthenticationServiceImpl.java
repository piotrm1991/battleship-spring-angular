package com.example.battleship.security.service.impl;

import com.example.battleship.security.request.LoginRequest;
import com.example.battleship.security.request.LogoutRequest;
import com.example.battleship.security.request.RefreshTokenRequest;
import com.example.battleship.security.request.SignupRequest;
import com.example.battleship.security.response.LoginResponse;
import com.example.battleship.security.response.RefreshTokenResponse;
import com.example.battleship.security.service.AuthenticationService;
import com.example.battleship.security.service.JwtService;
import com.example.battleship.security.service.RefreshTokenService;
import com.example.battleship.user.entity.User;
import com.example.battleship.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * Implementation of the AuthenticationService interface
 * for managing authentication-related requests.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final JwtService jwtService;
  private final UserService userService;
  private final AuthenticationManager authenticationManager;
  private final RefreshTokenService refreshTokenService;

  @Override
  public String signup(SignupRequest signupRequest) {
    log.info("Registering new user with login: {}", signupRequest.login());
    userService.createUser(signupRequest);

    return "Registration successful!";
  }

  @Override
  public LoginResponse authenticate(LoginRequest loginRequest) {
    log.info("Authenticating user with login: {}", loginRequest.login());
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginRequest.login(),
                    loginRequest.password()
            )
    );
    User authenticatedUser = userService.getUserByLogin(loginRequest.login());

    return  new LoginResponse(
            jwtService.generateToken(authenticatedUser),
            refreshTokenService.createRefreshToken(loginRequest.login())
    );
  }

  @Override
  public RefreshTokenResponse getRefreshedAccessToken(RefreshTokenRequest refreshTokenRequest) {
    log.info("Refreshing accessToken.");

    return refreshTokenService.getNewTokenByRefreshToken(refreshTokenRequest);
  }

  @Override
  public void logout(LogoutRequest logoutRequest) {
    log.info("Logging out user.");

    refreshTokenService.deleteRefreshToken(logoutRequest.refreshToken());
  }
}
