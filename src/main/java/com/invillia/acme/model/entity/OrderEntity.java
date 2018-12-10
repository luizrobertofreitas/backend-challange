package com.invillia.acme.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "orders")
public class OrderEntity implements Serializable {

  private static final Long REFUND_PERIOD_IN_DAYS = 10l;

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  @NotEmpty
  private String address;

  @NotNull
  private LocalDateTime confirmedAt;

  @NotNull
  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderPaymentEntity> payments;

  @NotNull
  @Size(min = 1)
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<OrderItemEntity> items;

  @Builder
  public OrderEntity(final String address, final Set<OrderItemEntity> items, final OrderStatus status) {
    this.address = address;
    this.items = items;
    this.status = status;
  }

  public OrderEntity withPayment(final OrderPaymentEntity orderPaymentEntity) {
    Optional.of(orderPaymentEntity).ifPresent(orderPayment -> {
      this.payments = Optional.ofNullable(this.payments).orElse(new ArrayList<>());
      orderPayment.setOrder(this);
      this.setStatus(OrderStatus.COMPLETE);
      this.payments.add(orderPayment);
    });
    return this;
  }

  public void refundItem(final Long orderItemId) {
    if (canRefund()) {
      Optional.of(items).ifPresent(
          items -> items.removeIf(item -> ObjectUtils.nullSafeEquals(item.getId(), orderItemId)));
    }
  }

  public void refund() {
    if (canRefund()) {
      OrderPaymentEntity lastConcludedPayment = getLastConcludedPayment();
      lastConcludedPayment.refund();
      status = OrderStatus.REFUNDED;
    }
  }

  public boolean canRefund() {
    OrderPaymentEntity lastConcludedPayment = getLastConcludedPayment();
    return lastConcludedPayment != null && isConfirmationInsideRefundPeriod();
  }

  public boolean isConfirmationInsideRefundPeriod() {
    return confirmedAt.isAfter(LocalDateTime.now().minusDays(REFUND_PERIOD_IN_DAYS));
  }

  public OrderPaymentEntity getLastOrderPayment() {
    return Optional.ofNullable(payments)
        .orElse(Collections.emptyList())
        .stream().sorted(Comparator.comparing(OrderPaymentEntity::getPaidAt).reversed())
        .findFirst()
        .orElse(null);
  }

  private void updateOrderItemReferences() {
    Optional.of(items).ifPresent(items -> items.forEach(item -> item.setOrder(this)));
  }

  private OrderPaymentEntity getLastConcludedPayment() {
    return Optional.ofNullable(payments)
        .orElse(Collections.emptyList())
        .stream()
        .filter(payment -> payment.isConcluded())
        .findFirst()
        .orElse(null);
  }

  @PrePersist
  private void prePersist() {
    this.confirmedAt = LocalDateTime.now();
    this.status = OrderStatus.PAYMENT_PENDING;
    updateOrderItemReferences();
  }

  @PreUpdate
  private void preUpdate() {
    updateOrderItemReferences();
  }
}
