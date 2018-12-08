package com.invillia.acme.resource.v1;

import com.invillia.acme.exception.ResultNotFoundException;
import com.invillia.acme.resource.v1.domain.ErrorResponse;
import com.invillia.acme.resource.v1.domain.PayloadErrorResponse;
import com.invillia.acme.util.MessageSourceUtils;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ResourceExceptionHandler {

  @Autowired
  private MessageSourceUtils messageSourceUtil;

  @ExceptionHandler(value = EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void entityNotFoundException(EntityNotFoundException entityNotFoundException) {
    log.warn(entityNotFoundException.getMessage());
    logExceptionOnDebugMode(entityNotFoundException);
  }

  @ExceptionHandler(value = ResultNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void resultNotFoundException(ResultNotFoundException resultNotFoundException) {
    log.warn(resultNotFoundException.getMessage());
    logExceptionOnDebugMode(resultNotFoundException);
  }

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public @ResponseBody
  PayloadErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
    log.warn(methodArgumentNotValidException.getMessage());
    return new PayloadErrorResponse(messageSourceUtil.getRequestPayloadInvalidMessage())
        .withFieldErrors(methodArgumentNotValidException.getBindingResult().getFieldErrors());
  }

  @ExceptionHandler(value = Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public @ResponseBody ErrorResponse exception(Exception exception) {
    log.error(exception.getMessage(), exception);
    logExceptionOnDebugMode(exception);
    return ErrorResponse.builder().message(messageSourceUtil.getDefaultErrorMessage()).build();
  }

  private void logExceptionOnDebugMode(final Exception exception) {
    log.debug(exception.getMessage(), exception);
  }
}