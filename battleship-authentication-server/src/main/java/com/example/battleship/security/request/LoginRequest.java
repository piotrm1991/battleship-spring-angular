package com.example.battleship.security.request;

import static com.example.battleship.util.AuthenticationMessageConstants.LOGIN_IS_REQUIRED;
import static com.example.battleship.util.AuthenticationMessageConstants.LOGIN_SIZE_MIN_MESSAGE;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * A record holding data for logging user.
 */
public record LoginRequest(

        @NotBlank(message = LOGIN_IS_REQUIRED)
        @Size(min = 5, message = LOGIN_SIZE_MIN_MESSAGE)
        String login,

        @NotBlank(message = "Password is required!")
        String password
) {}
