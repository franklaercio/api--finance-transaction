package io.pismo.transaction.domain.exceptions;

public class InternalServerException extends RuntimeException {

  public InternalServerException(String message) {
    super(message);
  }
}
