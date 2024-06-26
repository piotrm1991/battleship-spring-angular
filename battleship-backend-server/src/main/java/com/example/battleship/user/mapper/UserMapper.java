package com.example.battleship.user.mapper;

import com.example.battleship.security.request.SignupRequest;
import com.example.battleship.user.entity.User;
import com.example.battleship.user.request.UserCreate;
import com.example.battleship.user.request.UserUpdate;
import com.example.battleship.user.response.UserResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for mapping between User DTOs and entities.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class UserMapper {

  private final ObjectMapper mapper;

  /**
   * Maps a UserCreate DTO to a User entity.
   *
   * @param userCreate The UserCreate DTO to be mapped.
   * @return A User entity.
   */
  public User mapUserCreateToEntity(UserCreate userCreate) {
    log.info("Mapping user creation to user.");
    try {

      return mapper.readValue(mapper.writeValueAsString(userCreate), User.class);
    } catch (JsonProcessingException e) {

      throw new RuntimeException(
              "Error while mapping user creation to entity " + e.getMessage(), e);
    }
  }

  /**
   * Maps a User entity to a UserResponse DTO.
   *
   * @param user The User entity to be mapped.
   * @return A UserResponse DTO.
   */
  public UserResponse mapEntityToResponse(User user) {
    log.info("Mapping user to user response.");
    try {

      return mapper.readValue(mapper.writeValueAsString(user), UserResponse.class);
    } catch (JsonProcessingException e) {

      throw new RuntimeException(
              "Error while mapping user entity to response " + e.getMessage(), e);
    }
  }

  /**
   * Maps data from a UserUpdate object to a User entity for updating user information.
   *
   * @param user       The User entity to be updated.
   * @param userUpdate The UserUpdate object containing the updated user data.
   * @return The updated User entity.
   */
  public User mapUserUpdateToEntity(User user, UserUpdate userUpdate) {
    log.info("Mapping user update to user entity");

    if (userUpdate.login() != null) {
      user.setLogin(userUpdate.login());
    }
    if (userUpdate.role() != null) {
      user.setRole(userUpdate.role());
    }
    if (userUpdate.status() != null) {
      user.setStatus(userUpdate.status());
    }

    return user;
  }

  /**
   * Maps a SignupRequest DTO to a User entity.
   *
   * @param signupRequest The SignupRequest DTO to be mapped.
   * @return A User entity.
   */
  public User mapSignupRequestToEntity(SignupRequest signupRequest) {

    log.info("Mapping signup request to user.");
    try {

      return mapper.readValue(mapper.writeValueAsString(signupRequest), User.class);
    } catch (JsonProcessingException e) {

      throw new RuntimeException(
              "Error while mapping signup request to entity " + e.getMessage(), e);
    }
  }
}
