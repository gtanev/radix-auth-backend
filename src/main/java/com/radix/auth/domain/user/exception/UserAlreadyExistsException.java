package com.radix.auth.domain.user.exception;

public class UserAlreadyExistsException extends Exception {

  public UserAlreadyExistsException(String errorMessage) {
    super(errorMessage);
  }
}