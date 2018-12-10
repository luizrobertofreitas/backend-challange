package com.invillia.acme.resource.v1.mapper;

import com.invillia.acme.model.entity.OrderPaymentEntity;
import com.invillia.acme.resource.v1.domain.Payment;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

  public OrderPaymentEntity paymentToOrderPaymentEntity(final Payment payment) {
    return Optional.ofNullable(payment).map(p ->
        OrderPaymentEntity.builder()
            .creditCardNumber(p.getCreditCardNumber())
            .build())
        .orElse(null);
  }

  public Payment orderPaymentEntityToPayment(final OrderPaymentEntity orderPaymentEntity) {
    return Optional.ofNullable(orderPaymentEntity).map(p ->
        Payment.builder()
            .id(p.getId())
            .creditCardNumber(p.getCreditCardNumber())
            .paidAt(p.getPaidAt())
            .status(p.getStatus())
            .build())
        .orElse(null);
  }

}
