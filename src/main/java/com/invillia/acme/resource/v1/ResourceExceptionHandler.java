package com.invillia.acme.resource.v1;

import com.invillia.acme.exception.ResultNotFoundException;
import com.invillia.acme.resource.v1.domain.ErrorResponse;
import com.invillia.acme.util.MessageSourceUtils;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ResourceExceptionHandler {

  @Autowired
  private MessageSourceUtils messageSourceUtil;

  @ExceptionHandler(value = {
      EntityNotFoundException.class,
      ResultNotFoundException.class
  })
  @ResponseBody
  public ResponseEntity entityNotFoundException(EntityNotFoundException entityNotFoundException) {
    log.warn(entityNotFoundException.getMessage());
    logExceptionOnDebugMode(entityNotFoundException);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
        .message(messageSourceUtil.getResultNotFoundExceptionMessage())
        .status(HttpStatus.NOT_FOUND)
        .build());
  }
//
//  @ExceptionHandler(value = ResultNotFoundException.class)
//  @ResponseBody
//  public ResponseEntity resultNotFoundException(ResultNotFoundException resultNotFoundException) {
//    log.warn(resultNotFoundException.getMessage());
//    logExceptionOnDebugMode(resultNotFoundException);
//    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
//        .message(resultNotFoundException.getMessage()).status(HttpStatus.NOT_FOUND)
//        .build());
//  }

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  @ResponseBody
  public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
    log.warn(methodArgumentNotValidException.getMessage());
    return ResponseEntity.badRequest().body(ErrorResponse.builder()
        .message(messageSourceUtil.getRequestPayloadInvalidMessage())
        .status(HttpStatus.BAD_REQUEST)
        .build()
        .withFieldErrors(methodArgumentNotValidException.getBindingResult().getFieldErrors()));
  }

  @ExceptionHandler(value = Exception.class)
  @ResponseBody
  public ResponseEntity exception(Exception exception) {
    log.error(exception.getMessage(), exception);
    logExceptionOnDebugMode(exception);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ErrorResponse.builder()
            .message(exception.getLocalizedMessage())
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .build());
  }

  private void logExceptionOnDebugMode(final Exception exception) {
    log.debug(exception.getMessage(), exception);
  }
}