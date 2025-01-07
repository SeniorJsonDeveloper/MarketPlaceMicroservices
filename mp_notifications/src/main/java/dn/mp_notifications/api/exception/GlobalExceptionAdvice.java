package dn.mp_notifications.api.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> handleNotFoundException(NotFoundException e, WebRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND,e.getMessage(),request);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDto> handleNotFoundException(BadRequestException e, WebRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST,e.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception e, WebRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),request);
    }



    private ResponseEntity<ErrorDto> buildResponse(HttpStatus httpStatus,
                                                   String message,
                                                   WebRequest request) {
        return ResponseEntity.status(httpStatus)
                .body(ErrorDto.builder()
                        .code(httpStatus.value())
                        .message(message)
                        .details(request.getDescription(true))
                        .path(request.getContextPath())
                        .build());

    }
}
