package com.greenfox.seed0forever.p2pchat.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.greenfox.seed0forever.p2pchat.model.rest.StatusOkOrErrorRestMessage;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class JsonRestControllerAdvice extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
          HttpMessageNotReadableException ex,
          HttpHeaders headers, HttpStatus status, WebRequest request) {

    if (!(ex.getCause() instanceof JsonProcessingException)) {
      return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }

    StatusOkOrErrorRestMessage errorMessage = new StatusOkOrErrorRestMessage();
    errorMessage.setStatus("error");

    if (ex.getCause() instanceof JsonMappingException) {
      JsonMappingException jsonMappingException = (JsonMappingException) ex.getCause();
      List<Reference> jsonExceptionPaths = jsonMappingException.getPath();

      String fieldErrorsMessage = "JSON deserialization error with field(s): ";
      for (Reference jsonReference : jsonExceptionPaths) {
        fieldErrorsMessage += jsonReference.getFieldName();
        fieldErrorsMessage += ", ";
      }
      fieldErrorsMessage = fieldErrorsMessage.substring(0, fieldErrorsMessage.length() - 2);

      errorMessage.setMessage(fieldErrorsMessage);
      return new ResponseEntity(errorMessage, headers, status);

    } else if (ex.getCause() instanceof JsonParseException) {
      errorMessage.setMessage("parsing error: content does not conform to JSON syntax");
      return new ResponseEntity<>(errorMessage, headers, status);

    } else {
      errorMessage.setMessage("unspecified JSON processing exception");
      return new ResponseEntity<>(errorMessage, headers, status);
    }
  }

}
