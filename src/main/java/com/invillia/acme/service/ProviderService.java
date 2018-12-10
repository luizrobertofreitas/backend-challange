package com.invillia.acme.service;

import com.invillia.acme.model.entity.ProviderEntity;
import com.invillia.acme.model.repository.ProviderRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderService {

  @Autowired
  private ProviderRepository providerRepository;

  public ProviderEntity save(final ProviderEntity providerEntity) {
    return providerRepository.save(providerEntity);
  }

  public ProviderEntity update(final Long id, final ProviderEntity providerEntity) {
    ProviderEntity providerFound = getById(id);
    return save(providerFound.updateWith(providerEntity));
  }

  public ProviderEntity getById(final Long id) {
    return providerRepository.getOne(id);
  }

  public List<ProviderEntity> getByProviderParams(final ProviderEntity providerEntity) {
    return providerRepository.findByParams(providerEntity);
  }

}
