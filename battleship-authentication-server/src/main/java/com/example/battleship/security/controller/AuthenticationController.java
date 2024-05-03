package com.example.battleship.security.controller;

import com.example.battleship.security.request.LoginRequest;
import com.example.battleship.security.request.LogoutRequest;
import com.example.battleship.security.request.RefreshTokenRequest;
import com.example.battleship.security.request.SignupRequest;
import com.example.battleship.security.response.LoginResponse;
import com.example.battleship.security.response.RefreshTokenResponse;
import com.example.battleship.security.service.AuthenticationService;
import com.example.battleship.user.request.UserCreate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
@RestController
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.OK)
  public String register(@Valid @RequestBody SignupRequest signupRequest) {
    log.info("POST-request: Register new user: {}", signupRequest.login());

    return authenticationService.signup(signupRequest);
  }

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public LoginResponse authenticate(@Valid @RequestBody LoginRequest loginRequest) {
    log.info("POST-request: Login user: {}", loginRequest.login());

    return authenticationService.authenticate(loginRequest);
  }

  @PostMapping("/refreshToken")
  @ResponseStatus(HttpStatus.OK)
  public RefreshTokenResponse getNewToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
    log.info("POST-request: Refresh access token.");

    return authenticationService.getRefreshedToken(refreshTokenRequest);
  }

  @DeleteMapping("/logout")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public String logout(@RequestBody LogoutRequest logoutRequest) {
    log.info("DELETE-request: Logout user.");

    return authenticationService.logout(logoutRequest);
  }
}
