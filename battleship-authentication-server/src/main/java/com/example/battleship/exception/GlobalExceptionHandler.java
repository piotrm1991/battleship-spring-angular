package com.example.battleship.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler provides centralized handling of exceptions in the application.
 * It uses Spring's RestControllerAdvice annotation to intercept and handle exceptions thrown from
 * various controller methods.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles AccessDeniedException, indicating that the user
   * is not authorized to access a specific resource.
   *
   * @param ex The AccessDeniedException that was thrown.
   * @return A ResponseEntity with a forbidden status and an error message.
   */
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<String> handleAccessDeniedException(
      AccessDeniedException ex) {
    log.error(AccessDeniedException.class.getName() + " status: " + HttpStatus.FORBIDDEN.value());

    return new ResponseEntity<>("You are not authorize to see this content.",
        HttpStatus.FORBIDDEN);
  }

  /**
   * Handles BadRequestException and ValidationException,
   * indicating malformed or invalid client requests.
   *
   * @param ex The exception that was thrown.
   * @return A ResponseEntity with a bad request status and an error message.
   */
  @ExceptionHandler({
      BadRequestException.class,
      ValidationException.class
  })
  public ResponseEntity<String> handleBadRequestException(
      Exception ex) {
    log.error(ex.getMessage() + " status: " + HttpStatus.BAD_REQUEST.value());

    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  /**
   * Exception handler method to handle MethodArgumentNotValidException,
   * which occurs when validation
   * of method arguments annotated with @Valid fails.
   *
   * @param ex      The MethodArgumentNotValidException instance.
   * @param request The HttpServletRequest associated with the request.
   * @return A ResponseEntity containing a Map of validation errors and an HTTP status code of
   *         HttpStatus.BAD_REQUEST.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex, HttpServletRequest request) {
    List<String> errors = new ArrayList<>();

    ex.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));

    Map<String, List<String>> result = new HashMap<>();
    result.put("errors", errors);

    return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles EntityNotFoundException, indicating that a requested entity was not found.
   *
   * @param ex The EntityNotFoundException that was thrown.
   * @return A ResponseEntity with a not found status and an error message.
   */
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<String> handleEntityNotFoundException(
      EntityNotFoundException ex) {
    log.error(ex.getMessage() + " status: " + HttpStatus.NOT_FOUND.value());

    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  /**
   * Handles UnAuthenticatedException,
   * indicating that the user is not authenticated for a specific operation.
   *
   * @param ex The UnAuthenticationException that was thrown.
   * @return A ResponseEntity with an unauthorized status and an error message.
   */
  @ExceptionHandler(UnAuthenticatedException.class)
  public ResponseEntity<String> handleUnAuthenticationException(
      UnAuthenticatedException ex) {
    log.error(UnAuthenticatedException.class.getName()
        + " status: " + HttpStatus.UNAUTHORIZED.value());

    return new ResponseEntity<>("You must log in to your account.",
        HttpStatus.UNAUTHORIZED);
  }

  /**
   * Handles AccountStatusException,
   * indicating that the user account status is DISABLED.
   *
   * @param ex The AccountStatusException that was thrown.
   * @return A ResponseEntity with an account locked status and an error message.
   */
  @ExceptionHandler(AccountStatusException.class)
  public ResponseEntity<String> handleAccountStatusException(
          AccountStatusException ex) {
    log.error(AccountStatusException.class.getName()
            + " status: " + HttpStatus.FORBIDDEN.value());

    return new ResponseEntity<>("The account is locked.",
            HttpStatus.FORBIDDEN);
  }

  /**
   * Handles SignatureException,
   * indicating that the user tried to use wrong JWT accessToken.
   *
   * @param ex The SignatureException that was thrown.
   * @return A ResponseEntity with a wrong jwt status and an error message.
   */
  @ExceptionHandler(SignatureException.class)
  public ResponseEntity<String> handleSignatureException(
          SignatureException ex) {
    log.error(SignatureException.class.getName()
            + " status: " + HttpStatus.FORBIDDEN.value());

    return new ResponseEntity<>("The JWT signature is invalid.",
            HttpStatus.FORBIDDEN);
  }

  /**
   * Handles ExpiredJwtException,
   * indicating that the user tried to use JWT accessToken that has already expired.
   *
   * @param ex The ExpiredJwtException that was thrown.
   * @return A ResponseEntity with as expired access accessToken status and an error message.
   */
  @ExceptionHandler(value = {ExpiredJwtException.class})
  public ResponseEntity<Object> handleExpiredJwtException(
          ExpiredJwtException ex) {
    log.error(ExpiredJwtException.class.getName()
            + " status: " + HttpStatus.FORBIDDEN.value());

    return new ResponseEntity<>("The JWT accessToken has expired.",
            HttpStatus.FORBIDDEN);
  }

  /**
   * Handles ExpiredRefreshTokenException,
   * indicating that the user tried to refresh JWT access accessToken
   * with expired refresh accessToken.
   *
   * @param ex The ExpiredRefreshTokenException that was thrown.
   * @return A ResponseEntity with as expired refresh accessToken status and an error message.
   */
  @ExceptionHandler(value = {ExpiredRefreshTokenException.class})
  public ResponseEntity<Object> handleExpiredRefreshTokenException(
          ExpiredRefreshTokenException ex) {
    log.error(ExpiredRefreshTokenException.class.getName()
            + " status: " + HttpStatus.FORBIDDEN.value());

    return new ResponseEntity<>("Refresh accessToken is expired. Please login again!",
            HttpStatus.FORBIDDEN);
  }

  /**
   * Handles RefreshTokenNotFoundException,
   * indicating that the user tried to refresh JWT access accessToken with refresh accessToken,
   * that doesn't exist int database.
   *
   * @param ex The RefreshTokenNotFoundException that was thrown.
   * @return A ResponseEntity with as refresh accessToken not in
   *          database status and an error message.
   */
  @ExceptionHandler(value = {RefreshTokenNotFoundException.class})
  public ResponseEntity<Object> handleRefreshTokenNotFoundException(
          RefreshTokenNotFoundException ex
  ) {
    log.error(RefreshTokenNotFoundException.class.getName()
            + " status: " + HttpStatus.INTERNAL_SERVER_ERROR.value());

    return new ResponseEntity<>("Refresh accessToken error occurred.",
            HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Handles all other unhandled exceptions that might occur in the application.
   *
   * @param ex The exception that was thrown.
   * @return A ResponseEntity with an internal server error status and an error message.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleAllExceptions(
      Exception ex) {
    log.error(ex.getMessage() + " status: " + HttpStatus.INTERNAL_SERVER_ERROR.value());

    return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
