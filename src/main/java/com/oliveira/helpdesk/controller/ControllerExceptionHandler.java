package com.oliveira.helpdesk.controller;

import java.util.TooManyListenersException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.oliveira.helpdesk.exception.BusinessException;
import com.oliveira.helpdesk.exception.DataIntegrityException;
import com.oliveira.helpdesk.exception.ObjectNotFoundException;
import com.oliveira.helpdesk.exception.StandardError;
import com.oliveira.helpdesk.exception.TooManyRequestsException;
import com.oliveira.helpdesk.exception.ValidationError;

import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@Log4j2
public class ControllerExceptionHandler {

  @ExceptionHandler(TooManyListenersException.class)
  public ResponseEntity<StandardError> manyRequestsException(TooManyRequestsException e) {
    StandardError err = new StandardError(HttpStatus.TOO_MANY_REQUESTS.value(), e.getMessage(), e.getMessage(),
        System.currentTimeMillis());
    log.error(e.getMessage());
    return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(err);
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<StandardError> businessException(BusinessException e) {
    StandardError err = new StandardError(HttpStatus.CONFLICT.value(), e.getMessage(), e.getMessage(),
        System.currentTimeMillis());
    log.error(e.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
  }

  @ExceptionHandler(ObjectNotFoundException.class)
  public ResponseEntity<StandardError> ObjectNotFoundException(ObjectNotFoundException e) {
    StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), e.getMessage(),
        System.currentTimeMillis());
    log.error(e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
  }

  @ExceptionHandler(DataIntegrityException.class)
  public ResponseEntity<StandardError> DataIntegrityException(DataIntegrityException e) {
    StandardError err = new StandardError(HttpStatus.CONFLICT.value(), e.getMessage(), e.getMessage(),
        System.currentTimeMillis());
    log.error(e.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
  }

  @ExceptionHandler({ MethodArgumentNotValidException.class, HttpMessageNotReadableException.class })
  public ResponseEntity<ValidationError> handleValidationException(Exception ex) {

    ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(),
        ex.getMessage(), System.currentTimeMillis());

    if (ex instanceof MethodArgumentNotValidException) {
      MethodArgumentNotValidException manve = (MethodArgumentNotValidException) ex;

      for (FieldError fieldError : manve.getBindingResult().getFieldErrors()) {
        err.addError(fieldError.getField(), "", fieldError.getDefaultMessage());
      }
    }
    if (ex instanceof HttpMessageNotReadableException) {
      Throwable cause = ex.getCause();
      if (cause instanceof InvalidFormatException) {
        InvalidFormatException ife = (InvalidFormatException) cause;
        String fieldPath = ife.getPath().stream().map(ref -> ref.getFieldName())
            .collect(Collectors.joining("."));

        err.addError(fieldPath, ex.getMessage(), ex.getMessage());

      } else {
        err.addError("unknown", ex.getMessage(), ex.getMessage());
      }
    }

    log.error(err);
    log.error(err.getErrors());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
  }

}
