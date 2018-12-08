package com.invillia.acme.resource.v1.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ErrorResponse implements Serializable {
  private final LocalDateTime timestamp;
  private final String message;

  public ErrorResponse(final String message) {
    this.message = message;
    this.timestamp = LocalDateTime.now();
  }

}

