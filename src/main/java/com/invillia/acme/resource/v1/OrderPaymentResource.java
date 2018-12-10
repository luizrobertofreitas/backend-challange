package com.invillia.acme.resource.v1;

import com.invillia.acme.resource.v1.domain.Payment;
import com.invillia.acme.resource.v1.mapper.PaymentMapper;
import com.invillia.acme.service.OrderPaymentService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/orders/{orderId}/payments")
public class OrderPaymentResource {

  @Autowired
  private PaymentMapper paymentMapper;

  @Autowired
  private OrderPaymentService orderPaymentService;

  @PostMapping
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void create(@PathVariable("orderId") final Long orderId, @RequestBody @Valid Payment payment) {
    orderPaymentService.create(orderId, paymentMapper.paymentToOrderPaymentEntity(payment));
  }

}
