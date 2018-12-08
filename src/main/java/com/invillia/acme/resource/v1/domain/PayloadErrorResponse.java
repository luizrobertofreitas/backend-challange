package com.invillia.acme.resource.v1.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
public class PayloadErrorResponse extends ErrorResponse {
  private Map<String, String> errors;

  public PayloadErrorResponse(final String message) {
    super(message);
    errors = new HashMap<>();
  }

  public PayloadErrorResponse withFieldErrors(final Collection<FieldError> fieldErrors) {
    Optional.of(fieldErrors).ifPresent(this::processErrors);
    return this;
  }

  private void processErrors(final Collection<FieldError> fieldErrors) {
    fieldErrors.stream().forEach(this::processField);
  }

  private void processField(final FieldError fieldError) {
    errors.put(fieldError.getField(), fieldError.getDefaultMessage());
  }
}
