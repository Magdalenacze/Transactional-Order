package pl.akademiaspecjalistowit.transactionalorder.order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OrderControllerAdvice {

    @ExceptionHandler(OrderServiceException.class)
    public ResponseEntity handleOrderServiceException(OrderServiceException e){
         return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
             .body(e.getMessage());
    }

}
