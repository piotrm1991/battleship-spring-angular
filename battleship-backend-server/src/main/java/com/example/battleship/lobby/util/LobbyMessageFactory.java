package com.example.battleship.lobby.util;

import com.example.battleship.lobby.enums.LobbyMessageType;
import com.example.battleship.lobby.message.LobbyMessage;
import com.example.battleship.util.DateTimeConstants;
import java.time.LocalTime;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class responsible for preparing records of lobby messages.
 */
@UtilityClass
@Slf4j
public class LobbyMessageFactory {

  /**
   * Generates message for player going online.
   *
   * @param playerName String player name.
   * @return LobbyMessage record.
   */
  public static LobbyMessage generateSystemMessagePlayerConnected(String playerName) {

    return prepareLobbyMessageRecord(
         LobbyMessageType.CONNECT,
         "SYSTEM",
         String.format("Player %s is online!", playerName)
    );
  }

  /**
   * Generates message for player going offline.
   *
   * @param playerName String player name.
   * @return LobbyMessage record.
   */
  public static LobbyMessage generateSystemMessagePlayerDisconnected(String playerName) {

    return prepareLobbyMessageRecord(
            LobbyMessageType.DISCONNECT,
            "SYSTEM",
            String.format("Player %s is online!", playerName)
    );
  }

  /**
   * Prepare lobby message record.
   *
   * @param type LobbyMessageType enum.
   * @param sender String sender.
   * @param message String message content.
   * @return LobbyMessage record.
   */
  public static LobbyMessage prepareLobbyMessageRecord(
          LobbyMessageType type,
          String sender,
          String message
  ) {
    log.info("Preparing lobby message.");

    return new LobbyMessage(
            type,
            message,
            sender,
            LocalTime.now().format(DateTimeConstants.TIME_FORMATTER)
    );
  }
}
