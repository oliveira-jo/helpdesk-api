package com.oliveira.helpdesk.exception;

import java.io.Serializable;

public class StandardError implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer statusCode;
  private String errorCode;
  private String message;
  private Long timeStamp;

  public StandardError() {
  }

  public StandardError(Integer statusCode, String errorCode, String message, Long timeStamp) {
    this.statusCode = statusCode;
    this.errorCode = errorCode;
    this.message = message;
    this.timeStamp = timeStamp;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public Integer getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(Integer statusCode) {
    this.statusCode = statusCode;
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

  public Long getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(Long timeStamp) {
    this.timeStamp = timeStamp;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((statusCode == null) ? 0 : statusCode.hashCode());
    result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
    result = prime * result + ((message == null) ? 0 : message.hashCode());
    result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
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
    StandardError other = (StandardError) obj;
    if (statusCode == null) {
      if (other.statusCode != null)
        return false;
    } else if (!statusCode.equals(other.statusCode))
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
    if (timeStamp == null) {
      if (other.timeStamp != null)
        return false;
    } else if (!timeStamp.equals(other.timeStamp))
      return false;
    return true;
  }

}
