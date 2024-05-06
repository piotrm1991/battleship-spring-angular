package com.example.battleship.date.entity;

import com.example.battleship.date.deserialization.LocalDateDeserializer;
import com.example.battleship.date.serialization.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity for test serialization and deserialization of date.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestLocalDate {

  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  public LocalDate localDate;
}
