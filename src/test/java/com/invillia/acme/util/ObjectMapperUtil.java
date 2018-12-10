package com.invillia.acme.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static String serializeToString(Object objectToSerialize) throws JsonProcessingException {
    return OBJECT_MAPPER.writeValueAsString(objectToSerialize);
  }

}
