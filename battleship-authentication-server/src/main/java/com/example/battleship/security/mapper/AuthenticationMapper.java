package com.example.battleship.security.mapper;

import com.example.battleship.security.request.SignupRequest;
import com.example.battleship.user.request.UserCreate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper class for mapping between authentication-related DTOs and entities.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class AuthenticationMapper {

  private final ObjectMapper mapper;

  /**
   * Maps SignupRequest record to UserCreate record.
   *
   * @param signupRequest SignupRequest with data for registration.
   * @return UserCreate request for UserService.
   */
  public UserCreate mapSignupRequestToUserCreate(SignupRequest signupRequest) {
    log.info("Mapping signup request to user create.");
    try {

      return mapper.readValue(mapper.writeValueAsString(signupRequest), UserCreate.class);
    } catch (JsonProcessingException e) {

      throw new RuntimeException(
              "Error while mapping signup request to user create " + e.getMessage(), e);
    }
  }
}
