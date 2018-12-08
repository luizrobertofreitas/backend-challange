package com.invillia.acme.util;

public interface MessageKeys {
  String REQUEST_PAYLOAD_INVALID_KEY = "request.payload.invalid";
  String DEFAULT_ERROR_KEY = "an.error.occurred";
  String RESULT_NOT_FOUND_KEY = "result.not_found";
  String PROVIDER_NAME_NOT_EMPTY_KEY = "provider.name.not_empty";
  String PROVIDER_ADDRESS_NOT_EMPTY_KEY = "provider.address.not_empty";

  String PROVIDER_NAME_NOT_EMPTY_KEY_WITH_BRACKETS = "{"+PROVIDER_NAME_NOT_EMPTY_KEY+"}";
  String PROVIDER_ADDRESS_NOT_EMPTY_KEY_WITH_BRACKETS = "{"+PROVIDER_ADDRESS_NOT_EMPTY_KEY+"}";
}
