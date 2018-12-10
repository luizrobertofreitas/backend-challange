package com.invillia.acme.service;

import com.invillia.acme.model.entity.OrderEntity;
import com.invillia.acme.model.entity.OrderPaymentEntity;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class OrderPaymentService {

  @Autowired
  private OrderService orderService;

  @Async
  @Transactional
  public void create(final Long orderId, final OrderPaymentEntity orderPaymentEntity) {
    OrderEntity orderEntity = orderService.getById(orderId);
    orderService.save(orderEntity.withPayment(orderPaymentEntity));
  }

}
