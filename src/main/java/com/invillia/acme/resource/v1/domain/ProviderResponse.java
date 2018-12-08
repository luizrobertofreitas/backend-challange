package com.invillia.acme.resource.v1.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProviderResponse implements Serializable {
  private final Long id;
  private final String name, address;
  private final LocalDateTime createdAt;
}
