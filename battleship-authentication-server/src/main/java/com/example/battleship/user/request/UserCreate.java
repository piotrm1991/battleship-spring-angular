package com.example.battleship.user.request;

import com.example.battleship.user.enums.UserRoleEnum;
import com.example.battleship.validators.LoginAlreadyExists;
import com.example.battleship.validators.PasswordMatching;
import com.example.battleship.validators.StrongPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import static com.example.battleship.util.AuthenticationMessageConstants.CONFIRM_PASSWORD_IS_REQUIRED;
import static com.example.battleship.util.AuthenticationMessageConstants.LOGIN_IS_REQUIRED;
import static com.example.battleship.util.AuthenticationMessageConstants.LOGIN_SIZE_MIN_MESSAGE;
import static com.example.battleship.util.AuthenticationMessageConstants.PASSWORD_AND_CONFIRM_PASSWORD_MUST_BE_MATCHED;
import static com.example.battleship.util.AuthenticationMessageConstants.PASSWORD_IS_REQUIRED;
import static com.example.battleship.util.AuthenticationMessageConstants.PASSWORD_SIZE_MIN_MESSAGE;

/**
 * Represents a request object for creating a new user.
 */
@PasswordMatching(
        password = "password",
        confirmPassword = "confirmPassword",
        message = PASSWORD_AND_CONFIRM_PASSWORD_MUST_BE_MATCHED
)
public record UserCreate(
        @NotBlank(message = LOGIN_IS_REQUIRED)
        @Size(min = 6, message = LOGIN_SIZE_MIN_MESSAGE)
        @LoginAlreadyExists
        String login,

        @NotBlank(message = PASSWORD_IS_REQUIRED)
        @Size(min = 8, message = PASSWORD_SIZE_MIN_MESSAGE)
        @StrongPassword
        String password,

        @NotBlank(message = CONFIRM_PASSWORD_IS_REQUIRED)
        String confirmPassword,

        @NotNull(message = "You have to choose user status.")
        UserRoleEnum role
) {}