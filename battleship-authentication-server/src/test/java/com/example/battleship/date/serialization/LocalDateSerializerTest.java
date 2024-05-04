package com.example.battleship.date.serialization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.battleship.util.DateTimeConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class LocalDateSerializerTest {

  @Test
  public void serializeLocalDateToExpectedFormat() throws JsonProcessingException {

    ObjectMapper objectMapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addSerializer(LocalDate.class, new LocalDateSerializer());
    objectMapper.registerModule(module);

    LocalDate localDate = LocalDate.of(2023, 10, 4);
    String expectedSerializedDate = localDate.format(DateTimeConstants.DATE_FORMATTER);


    String serializedDate = objectMapper.writeValueAsString(localDate);


    assertEquals(expectedSerializedDate, serializedDate.replaceAll("\"", ""));
  }
}

