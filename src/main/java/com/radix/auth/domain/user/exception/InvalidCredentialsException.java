package com.radix.auth.domain.user.exception;

public class InvalidCredentialsException extends Exception {

  public InvalidCredentialsException(String errorMessage) {
    super(errorMessage);
  }
}