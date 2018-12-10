package com.invillia.acme.resource.v1.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class Provider implements Serializable {

  private final Long id;

  @NotEmpty
  private final String name;

  @NotEmpty
  private final String address;

  private final LocalDateTime createdAt;
}
