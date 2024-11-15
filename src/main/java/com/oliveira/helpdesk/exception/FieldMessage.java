package com.oliveira.helpdesk.exception;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldMessage implements Serializable {

  private static final long serialVersionUID = 1L;

  private String fieldName;
  private String errorCode;
  private String message;

}
