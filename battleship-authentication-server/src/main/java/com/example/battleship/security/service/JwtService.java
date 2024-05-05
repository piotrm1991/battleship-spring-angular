package com.example.battleship.security.service;

import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Service interface for managing JWT access accessToken related actions.
 */
public interface JwtService {

  /**
   * Extract user login from JWT accessToken.
   *
   * @param token String JWT accessToken.
   * @return String user login.
   */
  String extractLogin(String token);

  /**
   * Generate JWT accessToken based on user details.
   *
   * @param userDetails UserDetails user details of authenticated user.
   * @return String JWT accessToken.
   */
  String generateToken(UserDetails userDetails);

  /**
   * Generate JWT accessToken based on user details and extra claims.
   *
   * @param extraClaims Map extra claims for accessToken.
   * @param userDetails UserDetails user details of authenticated user.
   * @return String JWT accessToken.
   */
  String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

  /**
   * Check if JWT access accessToken is still valid.
   *
   * @param token String JWT access accessToken.
   * @param userDetails UserDetails.
   * @return boolean TRUE if valid, FALSE if not.
   */
  boolean isTokenValid(String token, UserDetails userDetails);
}
