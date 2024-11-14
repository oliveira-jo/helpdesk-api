package com.oliveira.helpdesk.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

  private static final long serialVersionUID = 1L;
  private List<FieldMessage> errors = new ArrayList<>();

  public ValidationError(Integer status, String error, Long timeStamp) {
    super(status, error, error, timeStamp);
  }

  public void addError(String fieldName, String errorCode, String msg) {
    errors.add(new FieldMessage(fieldName, errorCode, msg));
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((errors == null) ? 0 : errors.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    ValidationError other = (ValidationError) obj;
    if (errors == null) {
      if (other.errors != null)
        return false;
    } else if (!errors.equals(other.errors))
      return false;
    return true;
  }

}
