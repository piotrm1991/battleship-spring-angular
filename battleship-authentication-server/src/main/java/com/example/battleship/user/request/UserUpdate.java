package com.example.battleship.user.request;

import static com.example.battleship.util.AuthenticationMessageConstants.LOGIN_IS_REQUIRED;
import static com.example.battleship.util.AuthenticationMessageConstants.LOGIN_SIZE_MIN_MESSAGE;

import com.example.battleship.user.enums.UserRoleEnum;
import com.example.battleship.user.enums.UserStatusEnum;
import com.example.battleship.validators.LoginAlreadyExists;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Represents a request object for update user.
 */
@LoginAlreadyExists
public record UserUpdate(

        @NotNull(message = "Unexpected error!")
        Long id,

        @NotBlank(message = LOGIN_IS_REQUIRED)
        @Size(min = 4, message = LOGIN_SIZE_MIN_MESSAGE)
        String login,

        @NotNull(message = "You have to choose user role.")
        UserRoleEnum role,

        @NotNull(message = "You have to choose user status.")
        UserStatusEnum status
) {}
