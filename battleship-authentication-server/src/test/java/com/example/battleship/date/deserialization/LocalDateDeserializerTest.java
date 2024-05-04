package com.example.battleship.date.deserialization;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

import com.example.battleship.date.entity.TestLocalDate;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LocalDateDeserializerTest {

  private ObjectMapper mapper;

  @BeforeEach
  void setup() {
    mapper = new ObjectMapper();
  }

  @Test
  public void whenDeserializingValidLocalDate_thenCorrect() throws IOException {
    String json = "{\"localDate\":\"2023-01-01\"}";

    TestLocalDate testLocalDate = mapper.readValue(json, TestLocalDate.class);

    assertEquals(LocalDate.of(2023, 1, 1), testLocalDate.getLocalDate());
  }

  @Test
  public void whenDeserializingInvalidLocalDate_thenException(){
    String json = "{\"localDate\":\"202311-01-1310\"}";

    Exception exception = assertThrows(Exception.class,
            () -> mapper.readValue(json, TestLocalDate.class));

    String actualMessage = exception.getMessage();

    assertThat(actualMessage, containsString("Error while deserializing date use format: yyyy-MM-dd"));
  }
}