package com.invillia.acme.resource.v1.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

@Builder
@Getter
public class ErrorResponse implements Serializable {
  private final LocalDateTime timestamp = LocalDateTime.now();
  private final String message;
  private final HttpStatus status;
  private Map<String, String> errors;

  public ErrorResponse withFieldErrors(final Collection<FieldError> fieldErrors) {
    Optional.of(fieldErrors).ifPresent(this::processErrors);
    return this;
  }

  private void processErrors(final Collection<FieldError> fieldErrors) {
    errors = Optional.ofNullable(errors).orElse(new HashMap<>());
    fieldErrors.stream().forEach(this::processField);
  }

  private void processField(final FieldError fieldError) {
    errors.put(fieldError.getField(), fieldError.getDefaultMessage());
  }
}

