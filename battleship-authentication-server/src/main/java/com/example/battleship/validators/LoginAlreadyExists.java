package com.example.battleship.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.example.battleship.util.AuthenticationMessageConstants.LOGIN_ALREADY_EXISTS;

/**
 * Custom validation annotation to ensure that
 * the provided user login does not exist in the system.
 */
@Constraint(validatedBy = LoginValidator.class)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public  @interface LoginAlreadyExists {

  /**
   * The validation error message to be displayed when the login already exists.
   *
   * @return The validation error message.
   */
  String message() default LOGIN_ALREADY_EXISTS;

  /**
   * Specifies the validation groups to which this constraint belongs.
   *
   * @return An array of validation groups.
   */
  Class<?>[] groups() default {};

  /**
   * Specifies additional data to be provided when a validation error occurs.
   *
   * @return An array of payload classes.
   */
  Class<? extends Payload>[] payload() default {};
}
