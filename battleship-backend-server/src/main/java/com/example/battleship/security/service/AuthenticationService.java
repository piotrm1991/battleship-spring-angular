package com.example.battleship.security.service;

import com.example.battleship.security.request.LoginRequest;
import com.example.battleship.security.request.LogoutRequest;
import com.example.battleship.security.request.RefreshTokenRequest;
import com.example.battleship.security.request.SignupRequest;
import com.example.battleship.security.response.LoginResponse;
import com.example.battleship.security.response.RefreshTokenResponse;

/**
 * Service interface for managing authentication requests.
 */
public interface AuthenticationService {

  /**
   * Register new user.
   *
   * @param signupRequest SignupRequest record.
   * @return String message on signup status.
   */
  String signup(SignupRequest signupRequest);

  /**
   * Authenticate and login user.
   *
   * @param loginRecord LoginRequest record.
   * @return LoginResponse record with access and refresh tokens.
   */
  LoginResponse authenticate(LoginRequest loginRecord);

  /**
   * Get refreshed access accessToken based on refresh accessToken.
   *
   * @param refreshTokenRequest RefreshTokenRequest record with refresh accessToken.
   * @return RefreshTokenResponse record with new access accessToken.
   */
  RefreshTokenResponse getRefreshedAccessToken(RefreshTokenRequest refreshTokenRequest);

  /**
   * Logout action. Remove refresh accessToken from database.
   *
   * @param logoutRequest LogoutRequest record with refresh accessToken.
   */
  void logout(LogoutRequest logoutRequest);
}
