package com.invillia.acme.service;

import com.invillia.acme.model.entity.OrderEntity;
import com.invillia.acme.model.repository.OrderRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {

  @Autowired
  private OrderRepository orderRepository;

  public OrderEntity save(final OrderEntity orderEntity) {
    return orderRepository.save(orderEntity);
  }

  public OrderEntity getById(final Long id) {
    return orderRepository.getOne(id);
  }

  public List<OrderEntity> getByParams(final OrderEntity orderEntity) {
    return orderRepository.findByParams(orderEntity);
  }

  @Async
  @Transactional
  public void refund(final Long id) {
    OrderEntity orderEntity = getById(id);
    if (!orderEntity.canRefund())
      log.warn("Order: {} with status {} cannot be refunded", id, orderEntity.getStatus());
    orderEntity.refund();
    save(orderEntity);
  }

  @Async
  @Transactional
  public void refundItem(final Long id, final Long orderItemId) {
    OrderEntity orderEntity = getById(id);
    if (!orderEntity.canRefund())
      log.warn("Order Item: {}, for order {} with status {} cannot be refunded", orderItemId, id, orderEntity.getStatus());
    orderEntity.refundItem(orderItemId);
    save(orderEntity);
  }
}
