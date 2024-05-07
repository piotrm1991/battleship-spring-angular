package com.example.battleship.date.deserialization;

import com.example.battleship.exception.ValidationException;
import com.example.battleship.util.DateTimeConstants;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Custom deserializer for LocalDateTime objects to parse strings in "yyyy-MM-dd HH:mm:ss" format.
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

  /**
   * Deserializes a JSON string representing a date and time in "yyyy-MM-dd HH:mm:ss" format
   * into a LocalDateTime object.
   *
   * @param jsonParser the JSON parser containing the date and time string to be deserialized
   * @param deserializationContext the deserialization context
   * @return the deserialized LocalDateTime object
   * @throws IOException if an I/O error occurs during deserialization
   */
  @Override
  public LocalDateTime deserialize(JsonParser jsonParser,
                                   DeserializationContext deserializationContext)
          throws IOException {
    try {

      return LocalDateTime.parse(jsonParser.getText(), DateTimeConstants.DATE_TIME_FORMATTER);
    } catch (Exception e) {

      throw new ValidationException(
              "Error while deserializing date and time use format: yyyy-MM-dd HH:mm:ss");
    }
  }
}