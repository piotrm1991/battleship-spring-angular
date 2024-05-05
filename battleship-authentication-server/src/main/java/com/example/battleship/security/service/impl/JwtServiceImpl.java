package com.example.battleship.security.service.impl;

import com.example.battleship.security.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Implementation of JwtService for managing JWT access accessToken related actions.
 */
@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

  @Value("${spring.security.jwt.secret-key}")
  private String secretKey;

  @Value("${spring.security.jwt.expiration-time}")
  private long jwtExpiration;

  @Override
  public String extractLogin(String token) {
    log.info("Extracting user login from JWT accessToken.");

    return extractClaim(token, Claims::getSubject);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    log.info("Extracting claims from JWT accessToken.");
    final Claims claims = extractAllClaims(token);

    return claimsResolver.apply(claims);
  }

  @Override
  public String generateToken(UserDetails userDetails) {
    log.info("Generating JWT accessToken for user with login: {}", userDetails.getUsername());

    return generateToken(new HashMap<>(), userDetails);
  }

  @Override
  public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    log.info("Generating JWT accessToken.");

    return buildToken(extraClaims, userDetails, jwtExpiration);
  }

  private long getExpirationTime() {
    log.info("Getting default JWT expiration time.");

    return jwtExpiration;
  }

  private String buildToken(
        Map<String, Object> extraClaims,
        UserDetails userDetails,
        long expiration
  ) {
    log.info("Building JWT accessToken.");

    return Jwts
          .builder()
          .setClaims(extraClaims)
          .setSubject(userDetails.getUsername())
          .setIssuedAt(new Date(System.currentTimeMillis()))
          .setExpiration(new Date(System.currentTimeMillis() + expiration))
          .signWith(getSignInKey(), SignatureAlgorithm.HS256)
          .compact();
  }

  @Override
  public boolean isTokenValid(String token, UserDetails userDetails) {
    log.info("Checking if JWT accessToken is valid for user with login: {} ",
            userDetails.getUsername());

    final String username = extractLogin(token);

    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    log.info("Checking if accessToken is expired.");

    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    log.info("Extracting expiration date from accessToken.");

    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    log.info("Extracting all claims from accessToken");

    return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
  }

  private Key getSignInKey() {
    log.info("Preparing sign in key.");
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}