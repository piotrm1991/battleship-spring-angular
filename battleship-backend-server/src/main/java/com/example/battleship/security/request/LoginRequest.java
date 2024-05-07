package com.example.battleship.security.request;

import com.example.battleship.util.AuthenticationMessageConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * A record holding data for logging user.
 */
public record LoginRequest(

        @NotBlank(message = AuthenticationMessageConstants.LOGIN_IS_REQUIRED)
        @Size(min = 5, message = AuthenticationMessageConstants.LOGIN_SIZE_MIN_MESSAGE)
        String login,

        @NotBlank(message = "Password is required!")
        String password
) {}
