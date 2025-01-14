package dn.mp_orders.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.lang.reflect.InaccessibleObjectException;

@RestControllerAdvice
public class GlobalExceptionHandler  {

    @ExceptionHandler(OrderNotFound.class)
    public ResponseEntity<ErrorBody> handleNotFound(HttpStatus httpStatus, WebRequest request, OrderNotFound ex) {
        return ResponseEntity.ok(ErrorBody.builder()
                        .code(httpStatus.value())
                        .message(ex.getMessage())
                        .details(request.getDescription(false))
                        .path(request.getContextPath())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorBody> handleException(HttpStatus httpStatus, WebRequest request, Exception ex) {
        return ResponseEntity.ok(
                ErrorBody.builder()
                        .code(httpStatus.value())
                        .message(ex.getMessage())
                        .details(request.getDescription(false))
                        .path(request.getContextPath())
                        .build());
    }

    @ExceptionHandler(InaccessibleObjectException.class)
    public ResponseEntity<ErrorBody> handleException(HttpStatus httpStatus, WebRequest request, InaccessibleObjectException ex) {
        return ResponseEntity.ok(
                ErrorBody.builder()
                        .code(httpStatus.value())
                        .message(ex.getMessage())
                        .details(request.getDescription(true))
                        .path(request.getContextPath())
                        .build());
    }

}
