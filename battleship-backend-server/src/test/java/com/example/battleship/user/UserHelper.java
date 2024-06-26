package com.example.battleship.user;

import com.example.battleship.user.entity.User;
import com.example.battleship.user.enums.UserRoleEnum;
import com.example.battleship.user.enums.UserStatusEnum;
import com.example.battleship.user.request.UserCreate;
import com.example.battleship.user.request.UserUpdate;
import com.example.battleship.user.response.UserResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for tests with User entity.
 */
public class UserHelper {
  public static final String userUrlPath = "/users";
  public static final Long testUsersCount = 10L;

  public static final Long id = 1L;
  public static final String login = "LoginTest";
  public static final String password = "P4$$W0rD1";
  public static final UserRoleEnum role = UserRoleEnum.ADMIN;
  public static final UserStatusEnum status = UserStatusEnum.ENABLED;
  public static final LocalDateTime createDate = LocalDateTime.now();
  public static final LocalDateTime updateDate = LocalDateTime.now();
  public static final String updateLogin = "LoginTestUpdated";
  private static final String updatePassword = "PassWor$$1";
  private static final UserRoleEnum updateRole = UserRoleEnum.ADMIN;
  private static final UserStatusEnum updateStatus = UserStatusEnum.DISABLED;
  public static final Long numberOfUserAtStartup = 2L;

  /**
   * Creates User object.
   *
   * @return User entity.
   */
  public static User createUser() {

    return User.builder()
        .login(login)
        .password(password)
        .role(role)
        .status(status)
        .createDate(createDate)
        .build();
  }

  /**
   * Creates User object based on given data.
   *
   * @return User entity.
   */
  public static User createUser(
          String login,
          String password,
          UserRoleEnum role,
          UserStatusEnum status
  ) {

    return User.builder()
            .login(login)
            .password(password)
            .role(role)
            .status(status)
            .createDate(createDate)
            .build();
  }

  /**
   * Creates UserCreate record request.
   *
   * @return UserCreate record.
   */
  public static UserCreate createUserCreate() {

    return new UserCreate(
        login,
        password,
        password,
        role
    );
  }

  /**
   * Creates UserResponse record.
   *
   * @return UserResponse record.
   */
  public static UserResponse createUserResponse() {

    return new UserResponse(
        id,
        login,
        role,
        status,
        createDate.toString(),
        updateDate.toString()
    );
  }

  /**
   * Creates UserResponse record based on given data.
   *
   * @return UserResponse record.
   */
  public static UserResponse createUserResponse(
          String login,
          UserRoleEnum role,
          UserStatusEnum status
  ) {

    return new UserResponse(
            id,
            login,
            role,
            status,
            createDate.toString(),
            updateDate.toString()
    );
  }

  /**
   * Creates UserUpdate record request.
   *
   * @return UserUpdate record.
   */
  public static UserUpdate createUserUpdate() {

    return new UserUpdate(
        id,
        updateLogin,
        updateRole,
        updateStatus
    );
  }

  /**
   * Creates given number of User objects and returns it in List.
   *
   * @param numberOfUsers int number of requested User objects.
   * @return List of User entities.
   */
  public static List<User> createListWithUsers(int numberOfUsers) {

    List<User> users = new ArrayList<>();
    if (numberOfUsers > 0) {
      for (int i = 1; i <= numberOfUsers; i++) {
        users.add(
            User.builder()
              .login(login + i)
              .password(password)
              .role(role)
              .status(status)
              .createDate(createDate)
              .build()
        );
      }
    }

    return users;
  }

  /**
   * Creates User object with updated data.
   *
   * @return User entity.
   */
  public static User createUpdatedUser() {

    return User.builder()
        .login(updateLogin)
        .password(updatePassword)
        .role(updateRole)
        .status(updateStatus)
        .createDate(createDate)
        .updateDate(updateDate)
        .build();
  }

  /**
   * Creates UserResponse record with updated data.
   *
   * @return UserResponse record.
   */
  public static UserResponse createUpdatedUserResponse() {

    return new UserResponse(
        id,
        updateLogin,
        updateRole,
        updateStatus,
        createDate.toString(),
        updateDate.toString()
    );
  }

  /**
   * Creates List of User objects.
   *
   * @return List of User entities.
   */
  public static List<User> prepareUserList() {

    List<User> users = new ArrayList<>();
    if (testUsersCount > 0) {
      for (int i = 1; i <= testUsersCount; i++) {
        users.add(
            User.builder()
              .login(login + i)
              .password(password)
              .role(role)
              .status(status)
              .createDate(createDate)
              .build()
        );
      }
    }

    return users;
  }

  /**
   * Creates UserCreate record request with blank login.
   *
   * @return UserCreate record.
   */
  public static UserCreate createUserCreateBlankLogin() {

    return new UserCreate(
        "",
        password,
        password,
        role
    );
  }

  /**
   * Create UserUpdate record request with blank login.
   *
   * @return UserUpdate record.
   */
  public static UserUpdate createUserUpdateBlankLogin() {

    return new UserUpdate(
        id,
        "",
        updateRole,
        updateStatus
    );
  }

  /**
   * Creates UserUpdate record request with already existing login.
   *
   * @return UserUpdate record.
   */
  public static UserUpdate createUserUpdateWithExistingLogin() {

    return new UserUpdate(
        id,
        login,
        updateRole,
        updateStatus
    );
  }

  /**
   * Returns sum of created users and users created on startup.
   *
   * @return Long sum of Users.
   */
  public static Long numberOfUsersWithCreatedAtStartup() {

    return testUsersCount + numberOfUserAtStartup;
  }

  /**
   * Creates UserCreate record request with given login.
   *
   * @param loginNew String given user login.
   * @return UserCreate record.
   */
  public static UserCreate createUserCreateWithGivenLogin(String loginNew) {

    return new UserCreate(
          loginNew,
          password,
          password,
          role
    );
  }
}
