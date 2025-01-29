package dn.mp_warehouse.api.exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorBody {

    private int httpStatusCode;

    private String message;

    private String path;

    private String details;
}
