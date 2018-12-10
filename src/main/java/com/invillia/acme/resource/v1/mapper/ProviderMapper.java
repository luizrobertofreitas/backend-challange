package com.invillia.acme.resource.v1.mapper;

import com.invillia.acme.exception.ResultNotFoundException;
import com.invillia.acme.model.entity.ProviderEntity;
import com.invillia.acme.resource.v1.domain.Provider;
import com.invillia.acme.util.MessageSourceUtils;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProviderMapper {

  @Autowired
  private MessageSourceUtils messageSourceUtils;

  public ProviderEntity providerToProviderEntity(final Provider provider) {
    return ProviderEntity.builder()
        .address(provider.getAddress())
        .name(provider.getName())
        .build();
  }

  public Provider providerEntityToProvider(final ProviderEntity providerEntity) {
    return Optional.ofNullable(providerEntity)
        .map(p -> Provider.builder()
            .address(p.getAddress())
            .name(p.getName())
            .createdAt(p.getCreatedAt())
            .id(p.getId())
            .build())
        .orElseThrow(() -> new ResultNotFoundException(messageSourceUtils.getResultNotFoundExceptionMessage()));
  }

  public ProviderEntity providerParamsToProviderEntity(final String name, final String address) {
    return ProviderEntity.builder().name(name).address(address).build();
  }

  public List<Provider> providerEntityListToProviderList(final List<ProviderEntity> providerEntityList) {
    return Optional.ofNullable(providerEntityList)
        .orElse(Collections.emptyList())
        .stream()
        .map(this::providerEntityToProvider)
        .collect(Collectors.toList());
  }
}
