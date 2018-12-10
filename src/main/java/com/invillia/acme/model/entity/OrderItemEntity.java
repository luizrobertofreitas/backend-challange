package com.invillia.acme.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "order_items")
public class OrderItemEntity implements Serializable {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  @NotEmpty
  private String description;

  @NotNull
  @DecimalMin("0.01")
  private BigDecimal price;

  @Min(1L)
  private Integer quantity;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "order_id")
  private OrderEntity order;

  @Builder
  public OrderItemEntity(final String description, final BigDecimal price, final Integer quantity) {
    this.description = description;
    this.price = price;
    this.quantity = quantity;
  }
}
