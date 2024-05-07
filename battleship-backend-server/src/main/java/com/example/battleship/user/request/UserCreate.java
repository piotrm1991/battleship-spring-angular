package com.example.battleship.user.request;

import com.example.battleship.user.enums.UserRoleEnum;
import com.example.battleship.util.AuthenticationMessageConstants;
import com.example.battleship.validators.LoginAlreadyExists;
import com.example.battleship.validators.PasswordMatching;
import com.example.battleship.validators.StrongPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Represents a request object for creating a new user.
 */
@PasswordMatching(
        password = "password",
        confirmPassword = "confirmPassword",
        message = AuthenticationMessageConstants.PASSWORD_AND_CONFIRM_PASSWORD_MUST_BE_MATCHED
)
public record UserCreate(
        @NotBlank(message = AuthenticationMessageConstants.LOGIN_IS_REQUIRED)
        @Size(min = 6, message = AuthenticationMessageConstants.LOGIN_SIZE_MIN_MESSAGE)
        @LoginAlreadyExists
        String login,

        @NotBlank(message = AuthenticationMessageConstants.PASSWORD_IS_REQUIRED)
        @Size(min = 8, message = AuthenticationMessageConstants.PASSWORD_SIZE_MIN_MESSAGE)
        @StrongPassword
        String password,

        @NotBlank(message = AuthenticationMessageConstants.CONFIRM_PASSWORD_IS_REQUIRED)
        String confirmPassword,

        @NotNull(message = "You have to choose user status.")
        UserRoleEnum role
) {}