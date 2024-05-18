package com.example.battleship.lobby.util;

import java.time.LocalTime;
import com.example.battleship.lobby.enums.LobbyMessageType;
import com.example.battleship.lobby.message.LobbyMessage;
import com.example.battleship.util.DateTimeConstants;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class SystemMessageFactory {

  public static LobbyMessage generateSystemMessagePlayerConnected(String playerName) {

     return prepareLobbyMessageRecord(
             LobbyMessageType.CONNECT,
             "SYSTEM" ,
             String.format("Player %s is online!", playerName)
     );
  }

  public static LobbyMessage generateSystemMessagePlayerDisconnected(String playerName) {

    return prepareLobbyMessageRecord(
            LobbyMessageType.DISCONNECT,
            "SYSTEM" ,
            String.format("Player %s is online!", playerName)
    );
  }

  public LobbyMessage prepareLobbyMessageRecord(LobbyMessageType type, String sender, String message) {
    log.info("Preparing lobby message.");

    return new LobbyMessage(
            type,
            message,
            sender,
            LocalTime.now().format(DateTimeConstants.TIME_FORMATTER),
            ""
    );
  }
}
