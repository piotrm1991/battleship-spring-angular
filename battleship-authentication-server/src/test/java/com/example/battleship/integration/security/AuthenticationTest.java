package com.example.battleship.integration.security;

import static com.example.battleship.integration.security.AuthenticationHelper.createLoginRequest;
import static com.example.battleship.integration.security.AuthenticationHelper.createLogoutRequest;
import static com.example.battleship.integration.security.AuthenticationHelper.createSignupRequest;
import static com.example.battleship.integration.security.AuthenticationHelper.getLoginPath;
import static com.example.battleship.integration.security.AuthenticationHelper.getLogoutPath;
import static com.example.battleship.integration.security.AuthenticationHelper.getSignupPath;
import static com.example.battleship.util.AuthenticationMessageConstants.LOGIN_ALREADY_EXISTS;
import static com.example.battleship.util.AuthenticationMessageConstants.PASSWORD_AND_CONFIRM_PASSWORD_MUST_BE_MATCHED;
import static com.example.battleship.util.AuthenticationMessageConstants.STRONG_PASSWORD_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.battleship.security.entity.RefreshToken;
import com.example.battleship.security.repository.RefreshTokenRepository;
import com.example.battleship.security.request.LoginRequest;
import com.example.battleship.security.request.SignupRequest;
import com.example.battleship.security.response.LoginResponse;
import com.example.battleship.security.service.RefreshTokenService;
import com.example.battleship.shared.AbstractIntegrationTest;
import com.example.battleship.user.UserHelper;
import com.example.battleship.user.entity.User;
import com.example.battleship.user.enums.UserRoleEnum;
import com.example.battleship.user.repository.UserRepository;
import com.example.battleship.user.response.UserResponse;
import com.example.battleship.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Integration test for authentication of the user.
 */
public class AuthenticationTest extends AbstractIntegrationTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private RefreshTokenRepository refreshTokenRepository;

  @Autowired
  private RefreshTokenService refreshTokenService;

  private final ObjectMapper mapper = new ObjectMapper();

  private String signupPath;
  private String loginPath;
  private String logoutPath;

  private SignupRequest signupRequest;
  private LoginRequest loginRequest;

  @BeforeEach
  void setup() {
    signupRequest = createSignupRequest();
    signupPath = getSignupPath();
    loginPath = getLoginPath();
    logoutPath = getLogoutPath();
    loginRequest = createLoginRequest();
  }

  @Test
  @Transactional
  void givenCorrectSignUpRequest_whenSignup_thenCorrect() throws Exception {

    var response = mockMvc.perform(post(signupPath)
                    .content(mapper.writeValueAsString(signupRequest))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    String message = response.getResponse().getContentAsString();
    assertTrue(message.contains("Registration successful!"));
    assertEquals(1, userRepository.count());
  }

  @Test
  @Transactional
  void givenInCorrectSignUpRequest_whenSignup_thenException() throws Exception {

    mockMvc.perform(post(signupPath)
                    .content(mapper.writeValueAsString(signupRequest))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    assertEquals(1, userRepository.count());

    var response = mockMvc.perform(post(signupPath)
                    .content(mapper.writeValueAsString(signupRequest))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    String message = response.getResponse().getContentAsString();
    assertTrue(message.contains(LOGIN_ALREADY_EXISTS));
    assertEquals(1, userRepository.count());
  }

  @Test
  @Transactional
  void givenCorrectSignUpRequest_whenSignupMultipleAccounts_thenCorrect() throws Exception {

    mockMvc.perform(post(signupPath)
                    .content(mapper.writeValueAsString(signupRequest))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    assertEquals(1, userRepository.count());

    SignupRequest newSignupRequest = createSignupRequest(
            "Admin123",
            AuthenticationHelper.password,
            AuthenticationHelper.password
    );

    mockMvc.perform(post(signupPath)
                    .content(mapper.writeValueAsString(newSignupRequest))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    assertEquals(2, userRepository.count());
  }

  @Test
  @Transactional
  void givenInCorrectSignUpRequestIncorrectPassword_whenSignup_thenException() throws Exception {

    SignupRequest newSignupRequest = createSignupRequest(
            "Admin123",
            "Password!123",
            "Password!12345"
    );

    assertEquals(0, userRepository.count());

    var response = mockMvc.perform(post(signupPath)
                    .content(mapper.writeValueAsString(newSignupRequest))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();

    assertEquals(0, userRepository.count());
    String message = response.getResponse().getContentAsString();
    assertTrue(message.contains(PASSWORD_AND_CONFIRM_PASSWORD_MUST_BE_MATCHED));
    assertEquals(0, userRepository.count());

    newSignupRequest = createSignupRequest(
            "Admin123",
            "Password",
            "Password"
    );

    response = mockMvc.perform(post(signupPath)
                .content(mapper.writeValueAsString(newSignupRequest))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn();

    assertEquals(0, userRepository.count());
    message = response.getResponse().getContentAsString();
    assertTrue(message.contains(STRONG_PASSWORD_MESSAGE));
    assertEquals(0, userRepository.count());
  }

  @Test
  @Transactional
  void givenCorrectLoginRequest_whenAuthenticate_thenCorrect() throws Exception {
    UserResponse userResponse = userService.createUser(signupRequest);
    User user = userRepository.findById(userResponse.id()).get();
    user.setRole(UserRoleEnum.ADMIN);
    userRepository.save(user);

    var response = mockMvc.perform(post(loginPath)
                    .content(mapper.writeValueAsString(loginRequest))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    LoginResponse loginResponse = mapper
            .readValue(response.getResponse().getContentAsString(), LoginResponse.class);
    RefreshToken refreshToken = refreshTokenRepository.findByUserId(user.getId()).get();
    assertTrue(passwordEncoder.matches(loginResponse.refreshToken(), refreshToken.getToken()));

    mockMvc.perform(get(UserHelper.userUrlPath)
              .header("Authorization", "Bearer " + loginResponse.accessToken())
              .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  @Transactional
  void givenInCorrectLoginRequest_whenAuthenticate_thenException() throws Exception {
    UserResponse userResponse = userService.createUser(signupRequest);
    User user = userRepository.findById(userResponse.id()).get();
    user.setRole(UserRoleEnum.ADMIN);
    userRepository.save(user);

    LoginRequest loginRequestWrong = createLoginRequest(signupRequest.login(), "pppppp111!");

    var response = mockMvc.perform(post(loginPath)
                    .content(mapper.writeValueAsString(loginRequestWrong))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized())
            .andReturn();

    String message = response.getResponse().getContentAsString();
    assertTrue(message.contains("Wrong credentials. Try again!"));
  }

  @Test
  @Transactional
  void givenLogoutRequest_whenLogout_thenCorrect() throws Exception {
    UserResponse userResponse = userService.createUser(signupRequest);
    User user = userRepository.findById(userResponse.id()).get();
    user.setRole(UserRoleEnum.ADMIN);
    userRepository.save(user);

    var response = mockMvc.perform(post(loginPath)
                    .content(mapper.writeValueAsString(loginRequest))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    LoginResponse loginResponse = mapper
            .readValue(response.getResponse().getContentAsString(), LoginResponse.class);
    RefreshToken refreshToken = refreshTokenRepository.findByUserId(user.getId()).get();
    assertTrue(passwordEncoder.matches(loginResponse.refreshToken(), refreshToken.getToken()));

    mockMvc.perform(get(UserHelper.userUrlPath)
                    .header("Authorization", "Bearer " + loginResponse.accessToken())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    mockMvc.perform(delete(logoutPath)
                    .header("Authorization", "Bearer " + loginResponse.accessToken())
                    .content(mapper
                            .writeValueAsString(createLogoutRequest(loginResponse.refreshToken())))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andReturn();

    assertTrue(refreshTokenRepository.findByUserId(user.getId()).isEmpty());
  }
}
