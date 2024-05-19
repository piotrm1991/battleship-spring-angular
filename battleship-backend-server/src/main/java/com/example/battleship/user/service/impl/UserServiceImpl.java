package com.example.battleship.user.service.impl;

import com.example.battleship.exception.EntityNotFoundException;
import com.example.battleship.security.request.SignupRequest;
import com.example.battleship.user.entity.User;
import com.example.battleship.user.enums.UserRoleEnum;
import com.example.battleship.user.enums.UserStatusEnum;
import com.example.battleship.user.mapper.UserMapper;
import com.example.battleship.user.repository.UserRepository;
import com.example.battleship.user.request.UserCreate;
import com.example.battleship.user.request.UserUpdate;
import com.example.battleship.user.response.UserResponse;
import com.example.battleship.user.service.UserService;
import com.example.battleship.util.ExceptionMessagesConstants;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service implementation for user-related operations.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final BCryptPasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public void disableUserById(Long id) {
    log.info("Disabling user with ID: {}", id);

    User user = getUserById(id);
    user.setStatus(UserStatusEnum.DISABLED);
    save(user);
  }

  @Override
  @Transactional
  public UserResponse updateUser(Long id, UserUpdate userUpdate) {
    log.info("Updating user with ID: {}", id);

    User existingUser = getUserById(id);
    User updatedUser = userMapper.mapUserUpdateToEntity(existingUser, userUpdate);
    save(updatedUser);

    return userMapper.mapEntityToResponse(updatedUser);
  }

  @Override
  public Page<UserResponse> getAllUsers(Pageable pageable) {
    log.info("Getting all users and sorting them by (Pageable pageable): {}", pageable);

    return userRepository.findAll(pageable).map(userMapper::mapEntityToResponse);
  }

  @Override
  @Transactional
  public UserResponse getUserResponseById(Long id) {
    log.info("Getting user from database with id: {}", id);

    return userMapper.mapEntityToResponse(getUserById(id));
  }

  @Override
  @Transactional
  public UserResponse createUser(UserCreate userCreate) {
    log.info("Creating new user with login: {}", userCreate.login());

    User user = userMapper.mapUserCreateToEntity(userCreate);
    user.setStatus(UserStatusEnum.ENABLED);
    if (userCreate.role() == null) {
      user.setRole(UserRoleEnum.USER);
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    save(user);

    return userMapper.mapEntityToResponse(user);
  }

  @Override
  @Transactional
  public UserResponse createUser(SignupRequest signupRequest) {
    log.info("Creating new user with login: {}", signupRequest.login());

    User user = userMapper.mapSignupRequestToEntity(signupRequest);
    user.setStatus(UserStatusEnum.ENABLED);
    user.setRole(UserRoleEnum.USER);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    save(user);

    return userMapper.mapEntityToResponse(user);
  }

  @Override
  public boolean checkIfExistsByLogin(String login) {
    log.info("Checking if login: {} is already in database.", login);

    return userRepository.existsByLogin(login);
  }

  @Override
  @Transactional
  public User getUserById(Long userId) {
    log.info("Getting user from database with id: {}", userId);

    return userRepository.findById(userId).orElseThrow(()
        -> new EntityNotFoundException(
                ExceptionMessagesConstants
                        .createEntityNotExistsMessage(
                                User.class.getSimpleName(),
                                userId)
            )
    );
  }

  @Override
  @Transactional
  public User save(User user) {
    log.info("Saving user with login: {} into database.", user.getLogin());

    return userRepository.save(user);
  }

  @Override
  public User getUserByLogin(String login) {
    log.info("Searching User by login: {}", login);

    return userRepository.findByLogin(login).orElseThrow(()
        -> new EntityNotFoundException(
        ExceptionMessagesConstants.createUserWithLoginNotExistsMessage(login)));
  }

  @Override
  public User getCurrentlyLoggedUser() {
    log.info("Getting currently logged in user.");

    return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
