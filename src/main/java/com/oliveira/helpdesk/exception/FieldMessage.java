package com.oliveira.helpdesk.exception;

import java.io.Serializable;

public class FieldMessage implements Serializable {

  private static final long serialVersionUID = 1L;

  private String fieldName;
  private String errorCode;
  private String message;

  public FieldMessage() {
  }

  public FieldMessage(String fieldName, String errorCode, String message) {
    this.fieldName = fieldName;
    this.errorCode = errorCode;
    this.message = message;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
    result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
    result = prime * result + ((message == null) ? 0 : message.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    FieldMessage other = (FieldMessage) obj;
    if (fieldName == null) {
      if (other.fieldName != null)
        return false;
    } else if (!fieldName.equals(other.fieldName))
      return false;
    if (errorCode == null) {
      if (other.errorCode != null)
        return false;
    } else if (!errorCode.equals(other.errorCode))
      return false;
    if (message == null) {
      if (other.message != null)
        return false;
    } else if (!message.equals(other.message))
      return false;
    return true;
  }

}
