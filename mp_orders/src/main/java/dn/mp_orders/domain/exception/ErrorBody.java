package dn.mp_orders.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorBody {

    private int code;

    private String message;

    private String description;

    private HttpStatus httpStatus;
}
