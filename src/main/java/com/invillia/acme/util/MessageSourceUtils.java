package com.invillia.acme.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import static com.invillia.acme.util.MessageKeys.*;

@Component
public class MessageSourceUtils {

  @Autowired
  private MessageSource messageSource;

  public String getMessageByKey(final String key) {
    return messageSource.getMessage(key, null, null);
  }

  public String getMessageByKey(final String key, final Object ... params) {
    return messageSource.getMessage(key, params, null);
  }

  public String getRequestPayloadInvalidMessage() {
    return getMessageByKey(REQUEST_PAYLOAD_INVALID_KEY);
  }

  public String getResultNotFoundExceptionMessage(Object ... params) {
    return getMessageByKey(RESULT_NOT_FOUND_KEY, params);
  }

  public String getDefaultErrorMessage() {
    return getMessageByKey(DEFAULT_ERROR_KEY);
  }
}