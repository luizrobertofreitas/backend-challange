package com.invillia.acme.resource.v1.domain;

import com.invillia.acme.model.entity.OrderStatus;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Order implements Serializable {

  private final Long id;

  @NotEmpty
  private final String address;

  private final LocalDateTime confirmedAt;
  private final OrderStatus status;
  private final Payment payment;

  @Size(min = 1, max = 100)
  private final Set<OrderItem> items;
}
