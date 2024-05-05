package com.example.battleship.date.service.impl;

import com.example.battleship.date.service.DateService;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

/**
 * Implementation of the DateService interface providing methods to work with dates.
 */
@Service
public class DateServiceImpl implements DateService {

  @Override
  public LocalDate getCurrentDate() {

    return LocalDate.now();
  }
}
