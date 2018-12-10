package com.invillia.acme.resource.v1;

import com.invillia.acme.model.entity.OrderStatus;
import com.invillia.acme.resource.v1.domain.Order;
import com.invillia.acme.resource.v1.mapper.OrderMapper;
import com.invillia.acme.service.OrderService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/orders")
public class OrderResource {

  @Autowired
  private OrderMapper orderMapper;

  @Autowired
  private OrderService orderService;

  @PostMapping
  public ResponseEntity create(@RequestBody @Valid final Order order) {
    return ResponseEntity.ok(orderMapper.orderEntityToOrder(
        orderService.save(
            orderMapper.orderToOrderEntity(order))));
  }

  @GetMapping("/{id}")
  public ResponseEntity getById(@PathVariable("id") final Long id) {
    return ResponseEntity.ok(
        orderMapper.orderEntityToOrder(
            orderService.getById(id)));
  }

  @GetMapping
  public ResponseEntity getByParams(@RequestParam(name = "address", required = false) final String address,
      @RequestParam(name = "status", required = false) final OrderStatus status) {
    return ResponseEntity.ok(orderMapper.orderEntityListToOrderList(
        orderService.getByParams(
            orderMapper.orderParamsToOrderEntity(address, status))));
  }

  @DeleteMapping("/{id}/refund")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void refund(@PathVariable("id") final Long id) {
    orderService.refund(id);
  }

  @DeleteMapping("/{id}/items/{orderItemId}/refund")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void refundItem(@PathVariable("id") final Long id, @PathVariable("orderItemId") final Long orderItemId) {
    orderService.refundItem(id, orderItemId);
  }
}
