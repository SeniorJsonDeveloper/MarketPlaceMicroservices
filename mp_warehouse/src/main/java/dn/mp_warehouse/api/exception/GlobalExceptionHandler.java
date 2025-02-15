package dn.mp_warehouse.api.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorBody> catchProductNotFoundException(HttpStatus httpStatus,
                                                           WebRequest request,
                                                           ProductNotFoundException exception){
        return handleException(httpStatus,request,exception);
    }

    @ExceptionHandler(WarehouseNotFoundException.class)
    public ResponseEntity<ErrorBody> catchProductNotFoundException(HttpStatus httpStatus,
                                                                   WebRequest request,
                                                                   WarehouseNotFoundException exception){
        return handleException(httpStatus,request,exception);
    }

    @ExceptionHandler(ShopNotFoundException.class)
    public ResponseEntity<ErrorBody> catchProductNotFoundException(HttpStatus httpStatus,
                                                                   WebRequest request,
                                                                   ShopNotFoundException exception){
        return handleException(httpStatus,request,exception);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorBody> catchException(HttpStatus httpStatus,
                                                    WebRequest request,
                                                    Exception exception){
        return handleException(httpStatus,request,exception);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorBody> catchException(HttpStatus httpStatus,
                                                    WebRequest request,
                                                    BadRequestException exception){
        return handleException(httpStatus,request,exception);
    }

    @ExceptionHandler(DeveloperNameNotFoundException.class)
    public ResponseEntity<ErrorBody> catchException(HttpStatus httpStatus,
                                                    WebRequest request,
                                                    DeveloperNameNotFoundException exception){
        return handleException(httpStatus,request,exception);
    }

    @ExceptionHandler(DeliveryNotFoundException.class)
    public ResponseEntity<ErrorBody> catchException(HttpStatus httpStatus,
                                                    WebRequest request,
                                                    DeliveryNotFoundException exception){
        return handleException(httpStatus,request,exception);
    }


    private ResponseEntity<ErrorBody> handleException(HttpStatusCode httpStatusCode, WebRequest request,Exception e) {
        return  ResponseEntity.ok(
                ErrorBody.builder()
                        .httpStatusCode(httpStatusCode.value())
                        .message(e.getLocalizedMessage())
                        .details(request.getDescription(true))
                        .path(request.getContextPath())
                        .build());
    }
}
