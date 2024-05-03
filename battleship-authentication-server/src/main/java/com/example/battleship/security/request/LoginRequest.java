package com.example.battleship.security.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static com.example.battleship.util.AuthenticationMessageConstants.LOGIN_IS_REQUIRED;
import static com.example.battleship.util.AuthenticationMessageConstants.LOGIN_SIZE_MIN_MESSAGE;

public record LoginRequest(

        @NotBlank(message = LOGIN_IS_REQUIRED)
        @Size(min = 6, message = LOGIN_SIZE_MIN_MESSAGE)
        String login,

        @NotBlank(message = "Password is required!")
        String password
) {}
