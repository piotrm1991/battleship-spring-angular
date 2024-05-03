package com.example.battleship.security.service;

import com.example.battleship.security.request.LoginRequest;
import com.example.battleship.security.request.LogoutRequest;
import com.example.battleship.security.request.RefreshTokenRequest;
import com.example.battleship.security.request.SignupRequest;
import com.example.battleship.security.response.LoginResponse;
import com.example.battleship.security.response.RefreshTokenResponse;
import com.example.battleship.user.request.UserCreate;

public interface AuthenticationService {
  String signup(SignupRequest signupRequest);

  LoginResponse authenticate(LoginRequest loginRecord);

  RefreshTokenResponse getRefreshedToken(RefreshTokenRequest refreshTokenRequest);

  String logout(LogoutRequest logoutRequest);
}
