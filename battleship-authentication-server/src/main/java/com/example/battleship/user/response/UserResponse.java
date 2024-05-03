package com.example.battleship.user.response;

import com.example.battleship.user.enums.UserRoleEnum;
import com.example.battleship.user.enums.UserStatusEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents the response object containing user information.
 */
public record UserResponse(
        Long id,

        String login,

        UserRoleEnum role,

        UserStatusEnum status,

        String createDate,

        String updateDate
) {
}
