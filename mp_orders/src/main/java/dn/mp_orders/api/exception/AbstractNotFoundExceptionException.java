package dn.mp_orders.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AbstractNotFoundExceptionException extends RuntimeException {

    public AbstractNotFoundExceptionException(String message) {
        super(message);
    }

}
