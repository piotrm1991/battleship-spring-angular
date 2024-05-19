package com.example.battleship.globalchat.util;

import com.example.battleship.globalchat.enums.GlobalChatMessageType;
import com.example.battleship.globalchat.message.GlobalChatMessage;
import com.example.battleship.util.DateTimeConstants;
import java.time.LocalTime;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class responsible for generating GlobalChatMessage records.
 */
@UtilityClass
@Slf4j
public class GlobalChatMessageFactory {

  /**
   * Method prepares chat message record according to given data.
   *
   * @param type GlobalChatMessageType enum.
   * @param sender String sender.
   * @param message String message content.
   * @return GlobalChatMessage record.
   */
  public static GlobalChatMessage prepareChatMessageRecord(
          GlobalChatMessageType type,
          String sender,
          String message
  ) {
    log.info("Preparing chat message.");

    return new GlobalChatMessage(
            type,
            message,
            sender,
            LocalTime.now().format(DateTimeConstants.TIME_FORMATTER)
    );
  }
}
