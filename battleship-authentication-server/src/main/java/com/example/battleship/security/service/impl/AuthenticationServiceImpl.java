package com.example.battleship.security.service.impl;

import com.example.battleship.exception.ValidationException;
import com.example.battleship.security.entity.RefreshToken;
import com.example.battleship.security.mapper.AuthenticationMapper;
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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final JwtService jwtService;
  private final UserService userService;
  private final AuthenticationManager authenticationManager;
  private final AuthenticationMapper authenticationMapper;
  private final RefreshTokenService refreshTokenService;

  @Override
  public String signup(SignupRequest signupRequest) {
    log.info("Registering new user with login: {}", signupRequest.login());
    userService.createUser(authenticationMapper.mapSignupRequestToUserCreate(signupRequest));

    return "Registration successful!";
  }

  @Override
  public LoginResponse authenticate(LoginRequest loginRequest) {
    log.info("Authenticating user with login: {}", loginRequest.login());
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginRequest.login(),
                    loginRequest.password()
            )
    );
    if (!authentication.isAuthenticated()) {
      throw new ValidationException("Error during validation");
    }
    User authenticatedUser = userService.getUserByLogin(loginRequest.login());

    return  new LoginResponse(
            jwtService.generateToken(authenticatedUser),
            refreshTokenService.createRefreshToken(loginRequest.login())
    );
  }

  @Override
  public RefreshTokenResponse getRefreshedToken(RefreshTokenRequest refreshTokenRequest) {
    log.info("Refreshing token.");

    return refreshTokenService.getNewTokenByRefreshToken(refreshTokenRequest);
  }

  @Override
  public String logout(LogoutRequest logoutRequest) {
    log.info("Logging out user.");
    refreshTokenService.deleteRefreshToken(logoutRequest.refreshToken());

    return "Logged out was successful";
  }
}
