package com.example.battleship.date.serialization;

import com.example.battleship.util.DateTimeConstants;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Custom serializer for LocalDateTime objects to format them as strings
 * in "yyyy-MM-dd HH:mm:ss" format.
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

  /**
   * Serializes a LocalDateTime object to a string in "yyyy-MM-dd HH:mm:ss" format and writes it
   * to the JSON generator.
   *
   * @param localDateTime the LocalDateTime object to be serialized
   * @param jsonGenerator the JSON generator used to write the serialized string
   * @param serializerProvider the serializer provider
   * @throws IOException if an I/O error occurs during serialization
   */
  @Override
  public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator,
                        SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeString(localDateTime.format(DateTimeConstants.DATE_TIME_FORMATTER));
  }
}
