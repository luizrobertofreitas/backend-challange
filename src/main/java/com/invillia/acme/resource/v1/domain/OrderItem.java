package com.invillia.acme.resource.v1.domain;


import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItem implements Serializable {

  private Long id;

  @NotEmpty
  private String description;

  @NotNull
  @DecimalMin("0.01")
  private BigDecimal price;

  @Min(1L)
  private Integer quantity;
}
