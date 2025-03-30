package com.shukldi.ecommerce.handller;

import com.shukldi.ecommerce.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException{

     @ExceptionHandler(BusinessException.class)
     public ResponseEntity<String> handleBusinessException(BusinessException ex) {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
     }
}
