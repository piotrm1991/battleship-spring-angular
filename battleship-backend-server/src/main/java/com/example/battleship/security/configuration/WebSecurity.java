package com.example.battleship.security.configuration;

import com.example.battleship.user.entity.User;
import java.util.Objects;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Handles additional security checks.
 */
@Component
public class WebSecurity {

  /**
   * Checks if requested ID is the same as ID of currently logged user.
   *
   * @param authentication Authentication, part of spring context
   *                       that holds authentication information
   * @param id Long, ID of the request
   * @return boolean, true if IDs match, false if not
   */
  public boolean checkUserId(Authentication authentication, Long id) {

    return (Objects.equals(((User)
          authentication.getPrincipal()).getId(), id));
  }
}
