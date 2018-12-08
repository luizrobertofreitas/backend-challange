package com.invillia.acme.exception;

public class ResultNotFoundException extends RuntimeException {
  public ResultNotFoundException(final String message) {
    super(message);
  }
}
