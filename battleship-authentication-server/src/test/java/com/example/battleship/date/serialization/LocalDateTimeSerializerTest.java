package com.example.battleship.date.serialization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

public class LocalDateTimeSerializerTest {

  @Test
  public void serializeLocalDateTimeToExpectedFormat() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
    objectMapper.registerModule(module);

    LocalDateTime localDateTime = LocalDateTime.of(2023, 10, 4, 15, 30, 45);

    String expectedDateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    String expectedSerializedDateTime = localDateTime.format(DateTimeFormatter.ofPattern(expectedDateTimeFormat));

    String serializedDateTime = objectMapper.writeValueAsString(localDateTime);

    assertEquals(expectedSerializedDateTime, serializedDateTime.replaceAll("\"", ""));
  }
}