package com.invillia.acme.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "order_payments")
public class OrderPaymentEntity implements Serializable {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  @NotEmpty
  private String creditCardNumber;

  @NotNull
  private LocalDateTime paidAt;

  @NotNull
  @Enumerated(EnumType.STRING)
  private PaymentStatus status;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "order_id")
  private OrderEntity order;

  @Builder
  public OrderPaymentEntity(final String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
  }

  public void refund() {
    this.status = PaymentStatus.REFUNDED;
  }

  public boolean isConcluded() {
    return PaymentStatus.CONCLUDED == status;
  }

  @PrePersist
  private void prePersist() {
    this.paidAt = LocalDateTime.now();
    this.status = PaymentStatus.CONCLUDED;
  }
}
