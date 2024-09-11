package com.oliveira.helpdesk.exception;

public class AuthorizationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public AuthorizationException(String msg) {
    super(msg);
  }

}
