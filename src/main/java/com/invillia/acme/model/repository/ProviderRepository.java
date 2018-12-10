package com.invillia.acme.model.repository;

import com.invillia.acme.model.entity.ProviderEntity;
import com.invillia.acme.util.CustomStringUtils;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

public interface ProviderRepository extends JpaRepository<ProviderEntity, Long>, JpaSpecificationExecutor {

  default List<ProviderEntity> findByParams(final ProviderEntity providerEntity) {
    return findAll(Specification.where(nameIsLike(providerEntity.getName()))
        .and(addressIsLike(providerEntity.getAddress())));
  }

  static Specification<ProviderEntity> nameIsLike(final String name) {
    return StringUtils.isEmpty(name) ? null : (Specification<ProviderEntity>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"),
        CustomStringUtils.surroundStringWith(name, "%"));
  }

  static Specification<ProviderEntity> addressIsLike(final String address) {
    return StringUtils.isEmpty(address) ? null : (Specification<ProviderEntity>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("address"),
        CustomStringUtils.surroundStringWith(address, "%"));
  }
}
