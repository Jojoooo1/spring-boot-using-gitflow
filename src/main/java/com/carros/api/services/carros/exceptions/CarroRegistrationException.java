package com.carros.api.services.carros.exceptions;

public class CarroRegistrationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public CarroRegistrationException(String message) {
    super(message);
  }

  public CarroRegistrationException(String message, Throwable cause) {
    super(message, cause);
  }
}