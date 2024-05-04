package com.example.battleship.date.deserialization;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.battleship.date.entity.TestLocalDateTime;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LocalDateTimeDeserializerTest {

  private ObjectMapper mapper;

  @BeforeEach
  void setup() {
    mapper = new ObjectMapper();
  }

  @Test
  public void whenDeserializingValidLocalDateTime_thenCorrect() throws IOException {
    String json = "{\"localDateTime\":\"2023-01-01 12:30:45\"}";

    TestLocalDateTime testLocalDateTime = mapper.readValue(json, TestLocalDateTime.class);

    assertEquals(LocalDateTime.of(2023, 1, 1, 12, 30, 45), testLocalDateTime.getLocalDateTime());
  }

  @Test
  public void whenDeserializingInvalidLocalDateTime_thenException() {
    String json = "{\"localDateTime\":\"202311-01-13T10:15:30\"}";

    Exception exception = assertThrows(Exception.class,
            () -> mapper.readValue(json, TestLocalDateTime.class));

    String actualMessage = exception.getMessage();

    assertThat(actualMessage, containsString("Error while deserializing date and time use format: yyyy-MM-dd HH:mm:ss"));
  }
}
