package com.invillia.acme.resource.v1;

import com.invillia.acme.resource.v1.domain.ProviderRequest;
import com.invillia.acme.resource.v1.mapper.ProviderMapper;
import com.invillia.acme.service.ProviderService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/providers")
public class ProviderResource {

  @Autowired
  private ProviderService providerService;

  @Autowired
  private ProviderMapper providerMapper;

  @PostMapping
  public ResponseEntity create(@RequestBody @Valid final ProviderRequest providerRequest) {
    return ResponseEntity.status(HttpStatus.CREATED).body(providerMapper.providerToProviderResponse(
        providerService.save(
            providerMapper.providerRequestToProvider(providerRequest))));
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void update(@PathVariable("id") final Long id,
      @RequestBody @Valid final ProviderRequest providerRequest) {
    providerService.update(id, providerMapper.providerRequestToProvider(providerRequest));
  }

  @GetMapping("/{id}")
  public ResponseEntity getById(@PathVariable("id") final Long id) {
    return ResponseEntity.ok(
        providerMapper.providerToProviderResponse(providerService.getById(id)));
  }

  @GetMapping
  public ResponseEntity getByParams(@RequestParam(name = "name", required = false) final String name,
      @RequestParam(name = "address", required = false) final String address) {
    return ResponseEntity.ok(providerMapper.providerListToProviderResponseList(
        providerService.getByProviderParams(providerMapper.providerParamsToProvider(name, address))));
  }
}
