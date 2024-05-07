package com.example.battleship.integration.security;

import com.example.battleship.security.request.LogoutRequest;
import com.example.battleship.security.request.LoginRequest;
import com.example.battleship.security.request.SignupRequest;

/**
 * Helper class for security and authentication tests.
 */
public class AuthenticationHelper {
  public static String login = "Test123";
  public static String password = "Password!123";
  public static String authPath = "/auth";
  public static String signupPath = "/signup";
  public static String loginPath = "/login";
  public static String logoutPath = "/logout";

  /**
   * Create SignupRequest record.
   *
   * @return SignupRequest record.
   */
  public static SignupRequest createSignupRequest() {

    return new SignupRequest(
      login,
      password,
      password
    );
  }

  /**
   * Create SignupRequest record with given data.
   *
   * @param login String user login.
   * @param password String user password.
   * @param confirmPassword String user password.
   * @return SignupRequest record.
   */
  public static SignupRequest createSignupRequest(
          String login,
          String password,
          String confirmPassword
  ) {

    return new SignupRequest(
            login,
            password,
            confirmPassword
    );
  }

  /**
   * Create LoginRequest record.
   *
   * @return LoginRequest record.
   */
  public static LoginRequest createLoginRequest() {

    return new LoginRequest(
            login,
            password
    );
  }

  /**
   * Create LoginRequest record with given data.
   *
   * @param login String user login.
   * @param password String user password.
   * @return LoginRequest record.
   */
  public static LoginRequest createLoginRequest(String login, String password) {

    return new LoginRequest(
            login,
            password
    );
  }

  /**
   * Create LogoutRequest record with given refresh token.
   *
   * @return LogoutRequest record.
   */
  public static LogoutRequest createLogoutRequest(String refreshToken) {

    return new LogoutRequest(
            refreshToken
    );
  }

  /**
   * Get path to signup.
   *
   * @return String path.
   */
  public static String getSignupPath() {

    return authPath + signupPath;
  }

  /**
   * Get path to login.
   *
   * @return String path.
   */
  public static String getLoginPath() {

    return authPath + loginPath;
  }

  /**
   * Get path to logout.
   *
   * @return String path.
   */
  public static String getLogoutPath() {

    return authPath + logoutPath;
  }
}
