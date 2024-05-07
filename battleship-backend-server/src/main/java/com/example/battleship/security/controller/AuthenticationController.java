package com.example.battleship.security.controller;

import com.example.battleship.security.request.LoginRequest;
import com.example.battleship.security.request.SignupRequest;
import com.example.battleship.security.response.LoginResponse;
import com.example.battleship.security.service.AuthenticationService;
import com.example.battleship.security.request.LogoutRequest;
import com.example.battleship.security.request.RefreshTokenRequest;
import com.example.battleship.security.response.RefreshTokenResponse;
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

/**
 * Controller class for handling authentication related HTTP requests.
 */
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
@RestController
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  /**
   * Handles HTTP POST request for registering new user.
   *
   * @param signupRequest SignupRequest record.
   * @return String message about status of registration.
   */
  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.OK)
  public String register(@Valid @RequestBody SignupRequest signupRequest) {
    log.info("POST-request: Register new user: {}", signupRequest.login());

    return authenticationService.signup(signupRequest);
  }

  /**
   * Handles HTTP POST request for login a user.
   *
   * @param loginRequest LoginRequest record.
   * @return LoginResponse record with accessToken and refreshToken.
   */
  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public LoginResponse authenticate(@Valid @RequestBody LoginRequest loginRequest) {
    log.info("POST-request: Login user: {}", loginRequest.login());

    return authenticationService.authenticate(loginRequest);
  }

  /**
   * Handles HTTP POST request for refresh accessToken.
   *
   * @param refreshTokenRequest RefreshTokenRequest record with refresh accessToken.
   * @return RefreshTokenResponse with new access accessToken.
   */
  @PostMapping("/refreshToken")
  @ResponseStatus(HttpStatus.OK)
  public RefreshTokenResponse getNewToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
    log.info("POST-request: Refresh access accessToken.");

    return authenticationService.getRefreshedAccessToken(refreshTokenRequest);
  }

  /**
   * Handles HTTP DELETE request for logging out a user.
   *
   * @param logoutRequest LogoutRequest record with refresh accessToken.
   */
  @DeleteMapping("/logout")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void logout(@RequestBody LogoutRequest logoutRequest) {
    log.info("DELETE-request: Logout user.");

    authenticationService.logout(logoutRequest);
  }
}
