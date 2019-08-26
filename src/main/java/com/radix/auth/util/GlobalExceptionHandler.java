package com.radix.auth.util;

import com.radix.auth.domain.user.exception.InvalidCredentialsException;
import com.radix.auth.domain.user.exception.UserAlreadyExistsException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    return buildResponse(ex, status, ex.getBindingResult().getAllErrors().toArray(new ObjectError[0]));
  }

  @ExceptionHandler({InvalidCredentialsException.class, UserAlreadyExistsException.class})
  public ResponseEntity<Object> handleInvalidCredentials(Exception ex, WebRequest request) {
    HttpStatus status = (ex instanceof UserAlreadyExistsException) ? HttpStatus.CONFLICT : HttpStatus.UNAUTHORIZED;
    return buildResponse(ex, status);
  }


  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> handleUnexpectedException(Exception ex, WebRequest request) {
    return buildResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<Object> buildResponse(Exception ex, HttpStatus status, ObjectError... errors) {
    Map<String, Object> body = new LinkedHashMap<>();

    body.put("timestamp", LocalDateTime.now());
    body.put("status", status.value());

    if (errors.length > 0) {
      List<LinkedHashMap<String, String>> errorList =
          Arrays.stream(errors)
              .map(e -> new LinkedHashMap<String, String>() {
                {
                  put("field:", ((FieldError) e).getField());
                  put("value", String.valueOf(((FieldError) e).getRejectedValue()));
                  put("error", e.getDefaultMessage());
                }
              }).collect(Collectors.toList());
      body.put("errors", errorList);
    } else {
      body.put("error", status.getReasonPhrase());
      body.put("message", ex.getMessage());
    }

    return new ResponseEntity<>(body, new HttpHeaders(), status);
  }
}
