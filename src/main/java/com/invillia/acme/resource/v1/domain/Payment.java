package com.invillia.acme.resource.v1.domain;

import com.invillia.acme.model.entity.PaymentStatus;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Payment implements Serializable {
  private final Long id;
  @NotEmpty
  private final String creditCardNumber;
  private final PaymentStatus status;
  private final LocalDateTime paidAt;
}
