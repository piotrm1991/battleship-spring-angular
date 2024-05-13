package com.example.battleship.util;

import lombok.experimental.UtilityClass;

/**
 * Utility class provides global chat system messages.
 */
@UtilityClass
public class GlobalChatMessages {
  public static String getMessageNewUserJoinsChat(String login) {

    return String.format("%s joins the chat!", login);
  }

  public static String getMessageUserLeavesChat(String login) {

    return String.format("%s leaves the chat!", login);
  }
}
