package com.example.battleship.user.repository;

import com.example.battleship.user.entity.User;
import com.example.battleship.user.enums.UserStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for performing database operations on User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Checks if a user with the given login exists.
   *
   * @param login The login to check for existence.
   * @return True if a user with the email exists, false otherwise.
   */
  boolean existsByLogin(String login);

  /**
   * Get List of User objects by User status.
   *
   * @param status UserStatusEnum to search by.
   * @return List of User objects with given status.
   */
  List<User> findAllByStatus(UserStatusEnum status);

  Optional<User> findByLogin(String login);
}
