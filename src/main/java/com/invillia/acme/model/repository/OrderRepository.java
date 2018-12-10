package com.invillia.acme.model.repository;

import com.invillia.acme.model.entity.OrderEntity;
import com.invillia.acme.model.entity.OrderStatus;
import com.invillia.acme.util.CustomStringUtils;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>, JpaSpecificationExecutor<OrderEntity> {

  default List<OrderEntity> findByParams(final OrderEntity orderEntity) {
    return findAll(Specification.where(addressIsLike(orderEntity.getAddress()))
        .and(statusIsEquals(orderEntity.getStatus())));
  }

  static Specification<OrderEntity> addressIsLike(final String address) {
    return StringUtils.isEmpty(address) ? null : (Specification < OrderEntity >) (root, criteriaQuery, criteriaBuilder) ->
        criteriaBuilder.like(root.get("address"), CustomStringUtils.surroundStringWith(address, "%"));
  }

  static Specification<OrderEntity> statusIsEquals(final OrderStatus status) {
    return (Specification<OrderEntity>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
  }

}
