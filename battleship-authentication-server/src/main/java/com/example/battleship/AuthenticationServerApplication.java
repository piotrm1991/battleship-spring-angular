package com.example.battleship;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class AuthenticationServerApplication {

  public static void main(String[] args) {
    log.info("The application is running");
    SpringApplication.run(AuthenticationServerApplication.class, args);
  }
}