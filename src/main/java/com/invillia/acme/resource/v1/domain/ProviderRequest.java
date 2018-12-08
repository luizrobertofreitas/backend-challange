package com.invillia.acme.resource.v1.domain;

import com.invillia.acme.util.MessageKeys;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ProviderRequest implements Serializable {

  @NotEmpty(message = MessageKeys.PROVIDER_NAME_NOT_EMPTY_KEY_WITH_BRACKETS)
  private final String name;

  @NotEmpty(message = MessageKeys.PROVIDER_ADDRESS_NOT_EMPTY_KEY_WITH_BRACKETS)
  private final String address;
}