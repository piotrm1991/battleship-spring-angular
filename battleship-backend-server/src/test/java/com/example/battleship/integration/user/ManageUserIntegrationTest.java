package com.example.battleship.integration.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.battleship.shared.AbstractIntegrationTest;
import com.example.battleship.user.UserHelper;
import com.example.battleship.user.entity.User;
import com.example.battleship.user.enums.UserStatusEnum;
import com.example.battleship.user.repository.UserRepository;
import com.example.battleship.user.response.UserResponse;
import com.example.battleship.util.AuthenticationMessageConstants;
import com.example.battleship.util.ExceptionMessagesConstants;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Integration tests for POST, PUT, DELETE operations on User entity.
 */
public class ManageUserIntegrationTest extends AbstractIntegrationTest {

  @Autowired
  private UserRepository userRepository;

  private final ObjectMapper mapper =
      new ObjectMapper()
          .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
          .registerModule(new JavaTimeModule());

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenCorrectUserCreate_whenCreateUser_thenCorrect() throws Exception {
    var response = mockMvc.perform(MockMvcRequestBuilders
            .post(UserHelper.userUrlPath)
            .content(mapper.writeValueAsString(UserHelper.createUserCreate()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andReturn();

    UserResponse userResponse =
        mapper.readValue(response.getResponse().getContentAsString(), UserResponse.class);

    assertEquals(UserHelper.login, userResponse.login());
    assertEquals(1, userRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectUserCreateExistingName_whenCreateUser_thenException()
      throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .post(UserHelper.userUrlPath)
            .content(mapper.writeValueAsString(UserHelper.createUserCreate()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());

    var response = mockMvc.perform(MockMvcRequestBuilders
            .post(UserHelper.userUrlPath)
            .content(mapper.writeValueAsString(UserHelper.createUserCreate()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage.contains(AuthenticationMessageConstants.LOGIN_ALREADY_EXISTS));
    assertEquals(1, userRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectUserCreateBlankLogin_whenCreateUser_thenException()
      throws Exception {
    var response = mockMvc.perform(MockMvcRequestBuilders
            .post(UserHelper.userUrlPath)
            .content(mapper.writeValueAsString(UserHelper.createUserCreateBlankLogin()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage.contains(AuthenticationMessageConstants.LOGIN_IS_REQUIRED));
    assertEquals(0, userRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenCorrectUserUpdate_whenUpdateUser_thenCorrect() throws Exception {
    User user = userRepository.save(UserHelper.createUser());

    var response = mockMvc.perform(MockMvcRequestBuilders
            .put(createUrlPathWithId(UserHelper.userUrlPath, user.getId()))
            .content(mapper.writeValueAsString(UserHelper.createUserUpdate()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    UserResponse userResponse =
        mapper.readValue(response.getResponse().getContentAsString(), UserResponse.class);

    assertEquals(user.getId(), userResponse.id());
    assertEquals(UserHelper.updateLogin, userResponse.login());
    assertEquals(
        UserHelper.updateLogin,
        userRepository.findById(user.getId()).get().getLogin()
    );
    assertEquals(1, userRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectUserUpdateBlankLogin_whenUpdateUser_thenException()
      throws Exception {
    User user = userRepository.save(UserHelper.createUser());

    var response = mockMvc.perform(MockMvcRequestBuilders
            .put(createUrlPathWithId(UserHelper.userUrlPath, user.getId()))
            .content(mapper.writeValueAsString(UserHelper.createUserUpdateBlankLogin()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage.contains(AuthenticationMessageConstants.LOGIN_IS_REQUIRED));
    assertEquals(1, userRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectUserUpdateLoginAlreadyExists_whenUpdateUser_thenException()
      throws Exception {
    User user = userRepository.save(UserHelper.createUser());

    var response = mockMvc.perform(MockMvcRequestBuilders
            .put(createUrlPathWithId(UserHelper.userUrlPath, user.getId()))
            .content(mapper.writeValueAsString(UserHelper.createUserUpdateWithExistingLogin()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage.contains(AuthenticationMessageConstants.LOGIN_ALREADY_EXISTS));
    assertEquals(1, userRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectUserCreateLoginAlreadyExists_whenUpdateUser_thenException()
      throws Exception {
    User user = userRepository.save(UserHelper.createUser());

    var response = mockMvc.perform(MockMvcRequestBuilders
            .put(createUrlPathWithId(UserHelper.userUrlPath, user.getId()))
            .content(mapper.writeValueAsString(UserHelper.createUser()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    assertTrue(errorMessage.contains(AuthenticationMessageConstants.LOGIN_ALREADY_EXISTS));
    assertEquals(1, userRepository.findAll().size());
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectUserUpdateIdNotExists_whenUpdateUser_thenException()
      throws Exception {
    List<User> userList = UserHelper.prepareUserList();
    userList.forEach(a -> userRepository.save(a));
    Long invalidId = 100L;

    var response = mockMvc.perform(MockMvcRequestBuilders
            .put(createUrlPathWithId(UserHelper.userUrlPath, invalidId))
            .content(mapper.writeValueAsString(UserHelper.createUserUpdate()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    Assertions.assertEquals(
        ExceptionMessagesConstants.createEntityNotExistsMessage(User.class.getSimpleName(), invalidId),
        errorMessage
    );
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenCorrectId_whenDeleteUser_thenCorrect() throws Exception {
    List<User> userList = UserHelper.prepareUserList();
    userList.forEach(a -> userRepository.save(a));
    User user = userRepository.findAll().stream().findFirst().get();

    mockMvc.perform(MockMvcRequestBuilders
            .delete(createUrlPathWithId(UserHelper.userUrlPath, user.getId()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    assertEquals(
          UserHelper.testUsersCount - 1,
          userRepository.findAllByStatus(UserStatusEnum.ENABLED).size()
    );
  }

  @Test
  @Transactional
  @WithMockUser(roles = {"ADMIN"})
  public void givenIncorrectId_whenDeleteUser_thenException() throws Exception {
    List<User> userList = UserHelper.prepareUserList();
    userList.forEach(a -> userRepository.save(a));
    Long invalidId = 10000L;

    var response = mockMvc.perform(MockMvcRequestBuilders
            .delete(createUrlPathWithId(UserHelper.userUrlPath, invalidId))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andReturn();

    String errorMessage = response.getResponse().getContentAsString();

    Assertions.assertEquals(
        ExceptionMessagesConstants.createEntityNotExistsMessage(User.class.getSimpleName(), invalidId),
        errorMessage
    );
    assertEquals(UserHelper.testUsersCount, userRepository.findAll().size());
  }
}
