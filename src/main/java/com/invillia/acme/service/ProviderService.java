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

  public ProviderEntity save(final ProviderEntity provider) {
    return providerRepository.save(provider);
  }

  public ProviderEntity update(final Long id, final ProviderEntity provider) {
    ProviderEntity providerFound = getById(id);
    return save(providerFound.updateWith(provider));
  }

  public ProviderEntity getById(final Long id) {
    return providerRepository.getOne(id);
  }

  public List<ProviderEntity> getByProviderParams(final ProviderEntity provider) {
    return providerRepository.awesomeFinder(provider);
  }

}
