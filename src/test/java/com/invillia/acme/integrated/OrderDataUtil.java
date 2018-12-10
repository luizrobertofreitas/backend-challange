package com.invillia.acme.integrated;

import com.invillia.acme.model.entity.OrderEntity;
import com.invillia.acme.model.entity.OrderItemEntity;
import com.invillia.acme.resource.v1.domain.Order;
import com.invillia.acme.resource.v1.domain.OrderItem;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderDataUtil {

  public static List<Order> orders() {
    List<Order> orders = new ArrayList<>();
    orders.add(Order.builder().address("Address First").items(orderItems()).build());
    return orders;
  }

  public static Set<OrderItem> orderItems() {
    Set<OrderItem> orderItems = new HashSet<>();
    orderItems.add(OrderItem.builder().description("Item 1").price(BigDecimal.TEN).quantity(10).build());
    orderItems.add(OrderItem.builder().description("Item 2").price(BigDecimal.TEN).quantity(9).build());
//    orderItems.add(OrderItem.builder().description("Item 3").price(BigDecimal.TEN).quantity(5).build());
//    orderItems.add(OrderItem.builder().description("Item 4").price(BigDecimal.TEN).quantity(1).build());
    return orderItems;
  }

  public static List<OrderEntity> orderEntities() {
    List<OrderEntity> orderEntities = new ArrayList<>();
    orderEntities.add(OrderEntity.builder().address("Address First").items(orderItemEntities()).build());
//    orderEntities.add(OrderEntity.builder().address("Address Second").items(orderItemEntities()).build());
//    orderEntities.add(OrderEntity.builder().address("Address Third").items(orderItemEntities()).build());
    return orderEntities;
  }

  public static Set<OrderItemEntity> orderItemEntities() {
    Set<OrderItemEntity> orderItemEntities = new HashSet<>();
    orderItemEntities.add(OrderItemEntity.builder().description("Item 1").price(BigDecimal.TEN).quantity(10).build());
    orderItemEntities.add(OrderItemEntity.builder().description("Item 2").price(BigDecimal.TEN).quantity(9).build());
//    orderItemEntities.add(OrderItemEntity.builder().description("Item 3").price(BigDecimal.TEN).quantity(5).build());
//    orderItemEntities.add(OrderItemEntity.builder().description("Item 4").price(BigDecimal.TEN).quantity(1).build());
    return orderItemEntities;
  }

}
