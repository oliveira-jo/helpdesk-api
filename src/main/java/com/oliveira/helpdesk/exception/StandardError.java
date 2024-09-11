package com.oliveira.helpdesk.exception;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardError implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer statusCode;
  private String errorCode;
  private String message;
  private Long timeStamp;

}
