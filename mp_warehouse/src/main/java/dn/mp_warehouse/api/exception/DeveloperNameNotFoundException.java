package dn.mp_warehouse.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DeveloperNameNotFoundException extends RuntimeException {

    public DeveloperNameNotFoundException(String message) {
        super(message);
    }
}
