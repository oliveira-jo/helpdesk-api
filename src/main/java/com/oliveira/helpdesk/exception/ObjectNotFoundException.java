package com.oliveira.helpdesk.exception;

public class ObjectNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ObjectNotFoundException(String error) {
    super(error);
  }

}
