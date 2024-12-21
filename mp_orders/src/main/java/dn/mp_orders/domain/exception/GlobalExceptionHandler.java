package dn.mp_orders.domain.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler  {


    @ExceptionHandler(OrderNotFound.class) //TODO: Дописать обработчик
    public ResponseEntity<ErrorBody> handleNotFound(HttpStatus httpStatus, WebRequest request, OrderNotFound ex) {
        return ResponseEntity.ok(ErrorBody.builder()
                        .code(httpStatus.value())
                        .message(ex.getMessage())
                        .description(request.getDescription(false))
                        .build());
    }
}
