package com.invillia.acme.resource.v1.mapper;

import com.invillia.acme.exception.ResultNotFoundException;
import com.invillia.acme.model.entity.ProviderEntity;
import com.invillia.acme.resource.v1.domain.ProviderRequest;
import com.invillia.acme.resource.v1.domain.ProviderResponse;
import com.invillia.acme.util.MessageSourceUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProviderMapper {

  @Autowired
  private MessageSourceUtils messageSourceUtils;

  public ProviderEntity providerRequestToProvider(final ProviderRequest providerRequest) {
    return ProviderEntity.builder()
        .address(providerRequest.getAddress())
        .name(providerRequest.getName())
        .build();
  }

  public ProviderResponse providerToProviderResponse(final ProviderEntity provider) {
    Optional.ofNullable(provider).orElseThrow(() -> new ResultNotFoundException(messageSourceUtils.getResultNotFoundExceptionMessage()));
    return ProviderResponse.builder()
        .address(provider.getAddress())
        .name(provider.getName())
        .createdAt(provider.getCreatedAt())
        .id(provider.getId())
        .build();
  }

  public ProviderEntity providerParamsToProvider(final String name, final String address) {
    return ProviderEntity.builder().name(name).address(address).build();
  }

  public List<ProviderResponse> providerListToProviderResponseList(final List<ProviderEntity> providers) {
    Optional.ofNullable(providers).orElseThrow(() -> new ResultNotFoundException(messageSourceUtils.getResultNotFoundExceptionMessage()));
    List<ProviderResponse> providerResponseList = new ArrayList<>();
    providers.forEach(provider -> providerResponseList.add(providerToProviderResponse(provider)));
    return providerResponseList;
  }
}
