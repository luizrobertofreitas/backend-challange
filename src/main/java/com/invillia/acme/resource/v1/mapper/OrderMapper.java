package com.invillia.acme.resource.v1.mapper;

import com.invillia.acme.exception.ResultNotFoundException;
import com.invillia.acme.model.entity.OrderEntity;
import com.invillia.acme.model.entity.OrderItemEntity;
import com.invillia.acme.model.entity.OrderStatus;
import com.invillia.acme.resource.v1.domain.Order;
import com.invillia.acme.resource.v1.domain.OrderItem;
import com.invillia.acme.util.MessageSourceUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

  @Autowired
  private MessageSourceUtils messageSourceUtils;

  @Autowired
  private PaymentMapper paymentMapper;

  public List<Order> orderEntityListToOrderList(final List<OrderEntity> orderEntityList) {
    return Optional.ofNullable(orderEntityList)
        .orElse(new ArrayList<>())
        .stream()
        .map(this::orderEntityToOrder)
        .collect(Collectors.toList());
  }

  public Order orderEntityToOrder(final OrderEntity orderEntity) {
    return Optional.ofNullable(orderEntity)
        .map(order ->
            Order.builder()
                .address(order.getAddress())
                .confirmedAt(order.getConfirmedAt())
                .id(order.getId())
                .status(order.getStatus())
                .items(orderItemEntitySetToOrderItemSet(order.getItems()))
                .payment(paymentMapper.orderPaymentEntityToPayment(order.getLastOrderPayment()))
                .build())
        .orElseThrow(() ->  new ResultNotFoundException(messageSourceUtils.getResultNotFoundExceptionMessage()));
  }

  public OrderEntity orderToOrderEntity(final Order order) {
    return OrderEntity.builder()
        .address(order.getAddress())
        .items(orderItemSetToOrderItemEntitySet(order.getItems()))
        .build();
  }

  public Set<OrderItemEntity> orderItemSetToOrderItemEntitySet(final Set<OrderItem> orderItemSet) {
    return Optional.ofNullable(orderItemSet)
        .orElse(new HashSet<>())
        .stream()
        .map(this::orderItemToOrderItemEntity)
        .collect(Collectors.toSet());
  }

  public OrderItemEntity orderItemToOrderItemEntity(final OrderItem orderItem) {
    return Optional.of(orderItem).map(item ->
        OrderItemEntity.builder()
            .description(item.getDescription())
            .price(item.getPrice())
            .quantity(item.getQuantity())
            .build())
        .orElse(null);
  }

  public Set<OrderItem> orderItemEntitySetToOrderItemSet(final Set<OrderItemEntity> orderItemEntitySet) {
    return Optional.ofNullable(orderItemEntitySet)
        .orElse(new HashSet<>())
        .stream()
        .map(this::orderItemEntityToOrderItem)
        .collect(Collectors.toSet());
  }

  public OrderItem orderItemEntityToOrderItem(final OrderItemEntity orderItemEntity) {
    return Optional.of(orderItemEntity).map(item ->
        OrderItem.builder()
            .id(item.getId())
            .description(item.getDescription())
            .price(item.getPrice())
            .quantity(item.getQuantity())
            .build())
        .orElse(null);
  }

  public OrderEntity orderParamsToOrderEntity(final String address, final OrderStatus status) {
    return OrderEntity.builder().address(address).status(status).build();
  }

}
